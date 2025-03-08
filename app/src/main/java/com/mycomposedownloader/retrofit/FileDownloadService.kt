package com.mycomposedownloader.retrofit

import com.mycomposedownloader.models.DosyaRequestModel
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface FileDownloadService {

    @GET("your/api/endpoint/{filename}")                                                        // Örnek endpoint. Kendi API'nize göre düzenleyin.
    suspend fun downloadFile(@Path("filename") filename: String): ResponseBody                  // ResponseBody, ham veriyi almak için

    @POST("api/Synchronize/SynchronizeDatasUrunResimleri")

    suspend fun downloadResimDosyasi(@Body dosyaRequest: DosyaRequestModel,
                                     @Header("Content-Type") contentType: String = "application/json",     // ResponseBody, ham veriyi almak için)
                                     @Header("Content-Type") accept: String = "*/*"): Any       // ResponseBody, ham veriyi almak için)



}