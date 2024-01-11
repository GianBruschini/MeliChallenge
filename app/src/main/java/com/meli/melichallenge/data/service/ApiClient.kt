package com.meli.melichallenge.data.service

import com.meli.melichallenge.data.api.model.response.SearchResult
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {
    @GET("search")
    suspend fun searchProducts(@Query("q") query: String): SearchResult
}