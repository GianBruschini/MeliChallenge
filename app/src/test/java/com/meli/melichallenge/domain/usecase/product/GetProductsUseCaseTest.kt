package com.meli.melichallenge.domain.usecase.product

import com.meli.melichallenge.data.api.model.response.SearchResult
import com.meli.melichallenge.data.feature.product.ProductRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import retrofit2.Response

class GetProductsUseCaseTest {
    @RelaxedMockK
    @MockK
    private lateinit var productRepository: ProductRepository
    lateinit var getProductsUseCase: GetProductsUseCase
    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        productRepository = mockk()
        getProductsUseCase = GetProductsUseCase(productRepository)
    }

    @Test
    fun `see if product repository returns corrects values`() = runBlocking {
        val query = "Cellphone"
        val offset = 0
        val limit = 10
        val expectedResult = mockk<Response<SearchResult>>()

        coEvery {
            productRepository.searchProducts(query, offset, limit)
        } returns expectedResult

        val result = getProductsUseCase.searchProducts(query, offset, limit)
        assert(result == expectedResult)
    }


}