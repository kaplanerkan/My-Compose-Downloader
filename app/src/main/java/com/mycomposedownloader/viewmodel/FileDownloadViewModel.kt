package com.mycomposedownloader.viewmodel

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mycomposedownloader.models.DosyaRequestModel
import com.mycomposedownloader.models.DownloadStatus
import com.mycomposedownloader.retrofit.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


open class FileDownloadViewModel(application: Application) : AndroidViewModel(application) {

    // İndirme durumlarını tutan liste.  Compose UI'da gözlemlenebilir.
    open val downloadStatuses = mutableStateListOf<DownloadStatus>()
    private val context: Context by lazy { application.applicationContext }


    // Dosya listesi (örnek olarak, sunucudan alınabilir)
    private val fileList = mutableListOf(
        "image1.png", "image2.png", "image3.png", "image4.png", "image5.png"
    )


    fun startDownloads() {
        fileList.add("image6.png")
        fileList.add("image7.png")
        fileList.add("image8.png")
        fileList.add("image9.png")
        fileList.add("image10.png")


        // Her dosya için indirme işlemini başlat.
        fileList.forEach { filename ->
            // İndirme durumu listesinde zaten varsa, tekrar indirme.
            if (downloadStatuses.none { it.filename == filename }) {
                downloadStatuses.add(DownloadStatus(filename, 0, 0)) // Başlangıç durumu
                downloadFile(filename)
            }
        }
    }


    private fun downloadFile(filename: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                //val response = RetrofitClient.instance.downloadFile(filename)
                val dosyaRequestModel = DosyaRequestModel()
                dosyaRequestModel.fileName = filename
                dosyaRequestModel.name = filename
                dosyaRequestModel.image = filename
                dosyaRequestModel.resimId = 1


                val response = RetrofitClient.instance.downloadResimDosyasi(dosyaRequestModel)

                if (response is String && response.isBlank()) {
                    updateDownloadStatus(filename, 0, 0, error = "Boş dosya")

                } else if (response is String && response.isNotBlank()) {
                    val totalBytes = response.toByteArray()

                    updateDownloadStatus(
                        filename,
                        totalBytes.size.toLong(),
                        0
                    ) // Toplam boyutu güncelle

                    // Base64 veriyi al ve decode et
                    val base64Content = response // ResponseBody'i string'e çeviriyoruz.
                    val decodedBytes = Base64.decode(base64Content, Base64.DEFAULT)

                    saveFile(
                        filename,
                        decodedBytes,
                        decodedBytes.size.toLong() // Decode edilmiş verinin boyutunu kullanıyoruz
                    )
                } else {
                    updateDownloadStatus(filename, 0, 0, error = "Dosya mevcut degil veya boş")
                    return@launch
                }


            } catch (e: Exception) {
                updateDownloadStatus(
                    filename,
                    0,
                    0,
                    error = e.localizedMessage ?: "Bilinmeyen hata"
                )
            }
        }
    }


    private suspend fun saveFile(filename: String, byteArray: ByteArray, totalBytes: Long) {
        withContext(Dispatchers.IO) {
            try {

                val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)

                val dir = File(context.filesDir, "images") // Uygulama içi "images" dizini
                if (!dir.exists()) {
                    dir.mkdirs()
                }

                val pureFileName = filename.substringBefore(".") // .txt oncesi dosya ismi.
                val file = File(dir, "$pureFileName.png")
                // Dosya önceden mevcut ise sil
                if (file.exists()) {
                    file.delete()
                }

                FileOutputStream(file).use { out ->
                    //Byte array'i direk dosyaya yaz.
                    bitmap.compress(
                        Bitmap.CompressFormat.PNG,
                        100,
                        out
                    ) // bitmap'i PNG olarak kaydet
                    updateDownloadStatus(
                        filename,
                        totalBytes,
                        totalBytes,
                        isComplete = true
                    ) // Tamamlandı
                }

            } catch (e: IOException) {
                updateDownloadStatus(
                    filename,
                    totalBytes,
                    0,
                    error = e.localizedMessage ?: "Dosya kaydedilemedi"
                )
            }
        }
    }


    // İndirme durumunu güncelleyen yardımcı fonksiyon
    private fun updateDownloadStatus(
        filename: String,
        totalBytes: Long,
        downloadedBytes: Long,
        isComplete: Boolean = false,
        error: String? = null
    ) {
        val index = downloadStatuses.indexOfFirst { it.filename == filename }
        if (index != -1) {
            downloadStatuses[index] = downloadStatuses[index].copy(
                totalBytes = totalBytes,
                downloadedBytes = downloadedBytes,
                isComplete = isComplete,
                error = error
            )
        }
    }

    // İndirmeyi iptal etmek için (isteğe bağlı)
    private var downloadJobs = mutableMapOf<String, Job>()

    fun cancelDownload(filename: String) {
        downloadJobs[filename]?.cancel()
        downloadJobs.remove(filename)
        // Durumu güncelle (örneğin, iptal edildi olarak işaretle)
        updateDownloadStatus(filename, 0, 0, error = "İptal edildi")
    }

    fun clearDownloadStatus() {
        downloadStatuses.clear()
    }
}




