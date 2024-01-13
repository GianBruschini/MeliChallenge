package com.meli.melichallenge.data.feature.product

import com.meli.melichallenge.data.api.model.response.Product
import com.meli.melichallenge.domain.model.ResultValue
import javax.inject.Inject


class ProductRepository @Inject constructor(
    private val productRemoteDataSource: ProductRemoteDataSource,
) {
    suspend fun getProducts(aProduct: String): ResultValue<List<Product>> {
        val result = productRemoteDataSource.getProducts(aProduct)
        return result
    }
}

