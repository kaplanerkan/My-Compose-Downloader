package com.mycomposedownloader.models

import com.google.gson.annotations.SerializedName


data class DosyaRequestModel (

    @SerializedName("FileName")
    var fileName: String? = null,
    @SerializedName("Image")
    var image: String? = null,
    @SerializedName("ResimId")
    var resimId: Int = 0,
    @SerializedName("Name")
    var name: String? = null,


)