package com.meli.melichallenge.data.api.model.response

import com.google.gson.annotations.SerializedName

data class SearchResult(@SerializedName("site_id")
                        val idSite: String,
                        val results: List<Product>)
