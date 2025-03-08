package com.mycomposedownloader.ui.screens

import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mycomposedownloader.models.DownloadStatus
import com.mycomposedownloader.viewmodel.FakeFileDownloadViewModel
import com.mycomposedownloader.viewmodel.FileDownloadViewModel

@Composable
fun FileDownloadScreen(
    viewModel: FileDownloadViewModel = viewModel(factory = viewModelFactory() )
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(onClick = {
            viewModel.clearDownloadStatus()
            viewModel.startDownloads()
        }) {
            Text("İndirmeyi Başlat")
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
        Text(text = status.filename)
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
