package com.meli.melichallenge.data.api.model.response

import com.google.gson.annotations.SerializedName

data class ProductDetail(
    val id: String,
    val title: String,
    @SerializedName("category_id")
    val idCategory: String,
    val pictures: List<PictureDto>
)