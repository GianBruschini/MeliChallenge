package com.meli.melichallenge.domain.usecase.product

import com.meli.melichallenge.data.api.model.response.ProductDetail
import com.meli.melichallenge.data.api.model.response.SearchResult
import com.meli.melichallenge.data.feature.product.ProductRepository
import retrofit2.Response
import javax.inject.Inject

class GetProductDetailUseCase
@Inject constructor(
    private val productRepository: ProductRepository,
) {
    suspend fun getProductDetail(
        idProduct:String
    ): Response<ProductDetail> {
        return productRepository.getProductDetail(idProduct)
    }
}