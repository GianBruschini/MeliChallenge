package com.meli.melichallenge.domain.usecase.product

import com.meli.melichallenge.data.api.model.response.Product
import com.meli.melichallenge.data.feature.product.ProductRepository
import com.meli.melichallenge.domain.model.ResultValue
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetProductsUseCase
@Inject constructor(
    private val productRepository: ProductRepository,
) {
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
    suspend operator fun invoke(aProduct: String): ResultValue<List<Product>> =
        withContext(defaultDispatcher) {
            return@withContext try {
                when (val result = productRepository.getProducts(aProduct)) {
                    is ResultValue.Success -> result
                    is ResultValue.Error -> ResultValue.Error(result.exception)
                }
            } catch (e: Exception) {
                ResultValue.Error(e)
            }
        }
}
