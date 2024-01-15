package com.meli.melichallenge.data.feature.product

import com.meli.melichallenge.data.api.model.response.ItemResponse
import com.meli.melichallenge.data.api.model.response.SearchResult
import com.meli.melichallenge.data.service.ApiClient
import org.junit.Assert.*

import org.junit.Test
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import retrofit2.Response

class ProductRepositoryTest {

    private val apiClient: ApiClient = mockk()
    private val productRepository = ProductRepository(apiClient)

    @Test
    fun searchProducts_Success() = runBlocking {
        val product = "exampleProduct"
        val offset = 0
        val limit = 10
        val expectedResult: Response<SearchResult> = mockk()

        coEvery {
            apiClient.searchProducts(product, offset, limit)
        } returns expectedResult

        val result = productRepository.searchProducts(product, offset, limit)

        assert(result == expectedResult)
    }

    @Test
    fun getItem_Success() = runBlocking {
        val itemId = "exampleItemId"
        val expectedResult: Response<ItemResponse> = mockk()

        coEvery {
            apiClient.getItem(itemId)
        } returns expectedResult

        val result = productRepository.getItem(itemId)

        assert(result == expectedResult)
    }


}
