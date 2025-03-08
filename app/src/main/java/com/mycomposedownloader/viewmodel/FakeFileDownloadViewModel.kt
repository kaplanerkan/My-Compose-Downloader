package com.mycomposedownloader.viewmodel

import androidx.compose.runtime.mutableStateListOf
import com.mycomposedownloader.models.DownloadStatus


class FakeFileDownloadViewModel : FileDownloadViewModelInterface {
    override val downloadStatuses = mutableStateListOf<DownloadStatus>()

    init {
        // Fake download statuses
        downloadStatuses.add(DownloadStatus("image1.txt", 1000, 500, false, null))
        downloadStatuses.add(DownloadStatus("image2.txt", 1000, 1000, true, null))
        downloadStatuses.add(DownloadStatus("image3.txt", 1000, 200, false, "Hata"))
    }

    override fun clearDownloadStatus() {
        downloadStatuses.clear()
    }

    override fun startDownloads() {
        // No-op for preview
    }
}




interface FileDownloadViewModelInterface {
    val downloadStatuses: MutableList<DownloadStatus>
    fun clearDownloadStatus()
    fun startDownloads()
}

