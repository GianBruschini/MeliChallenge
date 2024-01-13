package com.meli.melichallenge.data.service

import com.meli.melichallenge.data.api.model.response.NetworkResponse
import com.meli.melichallenge.data.api.model.response.Product
import com.meli.melichallenge.data.api.model.response.SearchResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Single

interface ApiClient {
    @GET("sites/MLA/search")
    suspend fun searchProducts(@Query("q") query: String,
                               @Query("offset") offset: Int,
                               @Query("limit") limit: Int,): Response<SearchResult>
}