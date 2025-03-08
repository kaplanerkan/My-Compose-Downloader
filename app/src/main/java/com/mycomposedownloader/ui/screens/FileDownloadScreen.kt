package com.mycomposedownloader.ui.screens

import android.app.Application
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mycomposedownloader.components.MyCustomButtons
import com.mycomposedownloader.models.DownloadStatus
import com.mycomposedownloader.viewmodel.FakeFileDownloadViewModel
import com.mycomposedownloader.viewmodel.FileDownloadViewModel
import kotlinx.coroutines.delay

@Composable
fun FileDownloadScreen(
    viewModel: FileDownloadViewModel = viewModel(factory = viewModelFactory())
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {

            /**
             * VERSION 1: Depraced

            Button(
                modifier = Modifier.weight(1f),
                shape = MaterialTheme.shapes.extraSmall,// Köşe yuvarlaklığı için

                colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                onClick = {
                    isClicked = true
                    viewModel.clearDownloadStatus()
                    viewModel.startDownloads()
                }) {
                Text("İndirmeyi Başlat")
            }


            Button(
                modifier = Modifier.weight(1f),
                shape = MaterialTheme.shapes.extraSmall,// Köşe yuvarlaklığı için
                colors = ButtonDefaults.buttonColors(containerColor = buttonColorTemizle),
                onClick = {
                    isClickedTemizle = true
                    viewModel.clearDownloadStatus()
                }) {
                Text("TEMIZLE")
            }
          //  LaunchedEffect(isClicked, isClickedTemizle) olarak güncellemelisin ki her iki durum için de tetiklensin.
                LaunchedEffect(isClicked, isClickedTemizle) {
                    delay(300)
                    isClicked = false
                    isClickedTemizle = false
                }
             */



            MyCustomButtons(
                modifier = Modifier.weight(1f).padding(4.dp),
                text = "INDIR",
                shape = MaterialTheme.shapes.extraSmall,
                onClick = {
                    viewModel.clearDownloadStatus()
                    viewModel.startDownloads()
                }
            )
            Spacer(modifier = Modifier.width(8.dp))


            MyCustomButtons(
                modifier = Modifier.weight(1f).padding(4.dp),
                text = "TEMIZLE",
                shape = MaterialTheme.shapes.extraSmall,
                onClick = { viewModel.clearDownloadStatus() }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(viewModel.downloadStatuses) { status ->
                DownloadStatusItem(status)
            }
        }




    }
}


@Preview(showBackground = true)
@Composable
fun FileDownloadScreenPreview() {

    val application = LocalContext.current.applicationContext as Application
    // FakeViewModel kullanarak
    val fakeViewModel = FakeFileDownloadViewModel()

    // FileDownloadScreen fonksiyonunu Fake ViewModel ile çağırıyoruz
    //  FileDownloadScreen(viewModel = fakeViewModel)
}


@Composable
fun DownloadStatusItem(status: DownloadStatus) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {

        Text(text = status.filename)  // Burda kayit olan dosyanin ismi var

        if (status.isComplete) {
            Text("Tamamlandı", color = Color.Green)
        } else if (status.error != null) {
            Text("Hata: ${status.error}", color = Color.Red)
        } else {
            LinearProgressIndicator(
                progress = { if (status.totalBytes > 0) (status.downloadedBytes.toFloat() / status.totalBytes.toFloat()) else 0f },
                modifier = Modifier.fillMaxWidth(),
            )
            Text("${status.downloadedBytes} / ${status.totalBytes} bytes")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDownloadStatusItem() {
    DownloadStatusItem(
        status = DownloadStatus(
            filename = "example.txt",
            totalBytes = 1024,
            downloadedBytes = 512,
            isComplete = false,
            error = null
        )
    )
}


@Composable
fun viewModelFactory(): ViewModelProvider.Factory {
    val application = LocalContext.current.applicationContext as Application
    return ViewModelProvider.AndroidViewModelFactory.getInstance(application)
}
