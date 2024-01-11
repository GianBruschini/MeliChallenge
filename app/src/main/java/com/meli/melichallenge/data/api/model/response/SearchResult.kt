package com.meli.melichallenge.data.api.model.response

import com.google.gson.annotations.SerializedName

data class SearchResult(@SerializedName("site_id")
                        val siteId: String?,
                        val query: String?,
                        val paging: Paging,
                        val results: List<Product>)
