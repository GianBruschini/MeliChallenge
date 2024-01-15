package com.meli.melichallenge.domain.usecase.product

import com.meli.melichallenge.data.api.model.response.ItemResponse
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

class GetProductDetailUseCaseTest {
    @RelaxedMockK
    @MockK
    private lateinit var productRepository: ProductRepository
    lateinit var getProductsDetailUseCase: GetProductDetailUseCase
    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        productRepository = mockk()
        getProductsDetailUseCase = GetProductDetailUseCase(productRepository)
    }

    @Test
    fun `see if product repository returns corrects values`() = runBlocking {
        val itemId = "11111"
        val expectedResult = mockk<Response<ItemResponse>>()

        coEvery {
            productRepository.getItem(itemId)
        } returns expectedResult

        val result = getProductsDetailUseCase.getItem(itemId)
        assert(result == expectedResult)
    }

}