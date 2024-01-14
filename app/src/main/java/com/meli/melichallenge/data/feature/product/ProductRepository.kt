package com.meli.melichallenge.data.feature.product

import com.meli.melichallenge.data.api.model.response.ItemResponse
import com.meli.melichallenge.data.api.model.response.SearchResult
import com.meli.melichallenge.data.service.ApiClient
import retrofit2.Response
import javax.inject.Inject


class ProductRepository @Inject constructor(
    private val apiClient: ApiClient,
) {
    suspend fun searchProducts(product: String,
                            offset: Int,
                            limit: Int): Response<SearchResult> {
        return apiClient.searchProducts(product,offset,limit)

    }

    suspend fun getItem(itemId: String): Response<ItemResponse> {
            return apiClient.getItem(itemId)
    }

}

