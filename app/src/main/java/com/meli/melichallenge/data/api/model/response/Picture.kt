package com.meli.melichallenge.data.api.model.response

import com.google.gson.annotations.SerializedName

data class PictureDto(
    val id: String,
    @SerializedName("secure_url")
    val url: String
)