package com.meli.melichallenge.domain.usecase.product

import com.meli.melichallenge.data.api.model.response.SearchResult
import com.meli.melichallenge.data.feature.product.ProductRepository
import retrofit2.Response
import javax.inject.Inject

class GetProductsUseCase
@Inject constructor(
    private val productRepository: ProductRepository,
) {
    suspend fun searchProducts(
        query: String,
        offset: Int,
        limit: Int
    ): Response<SearchResult> {
        return productRepository.searchProducts(query, offset, limit)
    }
}
