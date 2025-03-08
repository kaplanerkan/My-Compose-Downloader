package com.mycomposedownloader.models

// İndirme durumunu ve ilerlemesini tutan model
data class DownloadStatus(
    val filename: String,
    val totalBytes: Long,
    val downloadedBytes: Long,
    val isComplete: Boolean = false,
    val error: String? = null
)


