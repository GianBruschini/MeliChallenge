package com.meli.melichallenge.data.feature.product

import com.meli.melichallenge.data.api.model.response.Product
import com.meli.melichallenge.data.api.model.response.ProductDetail
import com.meli.melichallenge.data.api.model.response.SearchResult
import com.meli.melichallenge.data.service.ApiClient
import com.meli.melichallenge.domain.model.ResultValue
import retrofit2.Response
import rx.Single
import javax.inject.Inject


class ProductRepository @Inject constructor(
    private val apiClient: ApiClient,
) {
    suspend fun searchProducts(product: String,
                            offset: Int,
                            limit: Int): Response<SearchResult> {
        return apiClient.searchProducts(product,offset,limit)

    }

    suspend fun getProductDetail(idProduct: String): Response<ProductDetail> {
        return apiClient.getProductDetails(idProduct)
    }
}

