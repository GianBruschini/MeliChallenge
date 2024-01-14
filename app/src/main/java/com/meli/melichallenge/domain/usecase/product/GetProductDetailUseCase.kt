package com.meli.melichallenge.domain.usecase.product

import android.util.Log
import com.meli.melichallenge.data.api.model.response.ItemResponse
import com.meli.melichallenge.data.api.model.response.SearchResult
import com.meli.melichallenge.data.feature.product.ProductRepository
import retrofit2.Response
import javax.inject.Inject

class GetProductDetailUseCase @Inject constructor(
    private val productRepository: ProductRepository,
) {
    suspend fun getItem(
        itemId:String
    ): Response<ItemResponse> {

        return productRepository.getItem(itemId)
    }
}