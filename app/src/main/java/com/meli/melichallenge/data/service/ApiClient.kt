package com.meli.melichallenge.data.service


import com.meli.melichallenge.data.api.model.response.ItemResponse
import com.meli.melichallenge.data.api.model.response.SearchResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiClient {
    @GET("sites/MLA/search")
    suspend fun searchProducts(@Query("q") query: String,
                               @Query("offset") offset: Int,
                               @Query("limit") limit: Int,): Response<SearchResult>


    @GET("items/{itemId}")
    suspend fun getItem(@Path("itemId") itemId: String): Response<ItemResponse>



}