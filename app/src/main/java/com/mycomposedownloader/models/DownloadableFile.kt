package com.mycomposedownloader.models

// İndirilecek dosya bilgilerini tutan model
data class DownloadableFile(
    val filename: String,
    val base64Content: String
)
