package com.meli.melichallenge.data.feature.product


import com.google.gson.Gson
import com.meli.melichallenge.data.api.BaseRemoteDataSource
import com.meli.melichallenge.data.api.model.response.Product
import com.meli.melichallenge.data.api.model.response.SearchResult
import com.meli.melichallenge.data.service.ApiClient
import com.meli.melichallenge.domain.model.ResultValue
import javax.inject.Inject

open class ProductRemoteDataSource @Inject constructor(
    private val apiClient: ApiClient,
    gson: Gson,
) : BaseRemoteDataSource(gson) {

    internal suspend fun getProducts(aProduct: String): ResultValue<List<Product>> {
        return getResponse(
            request = {
                apiClient.searchProducts(aProduct)
            },
        )
    }
}

