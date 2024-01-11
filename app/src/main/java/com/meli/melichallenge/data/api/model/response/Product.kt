package com.meli.melichallenge.data.api.model.response

import android.os.Parcelable

data class Product(
    val id: String,
    val title: String,
    val price: Double,
    val thumbnail: String,
    val permalink: String
)
