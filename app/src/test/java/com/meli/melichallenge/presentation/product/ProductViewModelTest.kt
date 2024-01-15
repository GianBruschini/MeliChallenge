package com.meli.melichallenge.presentation.product

import org.junit.Assert.*

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.meli.melichallenge.data.api.model.response.Product
import com.meli.melichallenge.data.api.model.response.SearchResult
import com.meli.melichallenge.domain.usecase.product.GetProductDetailUseCase
import com.meli.melichallenge.domain.usecase.product.GetProductsUseCase
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


@ExperimentalCoroutinesApi
class ProductViewModelTest {


    @RelaxedMockK
    private lateinit var getProductsUseCase: GetProductsUseCase

    @RelaxedMockK
    private lateinit var productViewModel: ProductViewModel
    private lateinit var context: Context

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        context = mockk(relaxed = true)
        productViewModel = ProductViewModel(getProductsUseCase, context)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when viewmodel is created at the first time, get all products and set first livedata value`() =
        runTest {
            // Given
            val mockProductsList = listOf(
                Product("1", "Product 1", 10.0, "thumbnail1", "permalink1"),
                Product("2", "Product 2", 20.0, "thumbnail2", "permalink2"),

            )

            coEvery {
                getProductsUseCase.searchProducts(any(), any(), any())
            } returns Response.success(SearchResult("",mockProductsList))

            // When
            productViewModel.getProducts("someProduct")

            // Then
            val observedList = productViewModel.productsList.getOrAwaitValue()


            assertEquals(mockProductsList, observedList)
        }

    // thiis is a helper function to observe LiveData value in a test
    private suspend fun <T> LiveData<T>.getOrAwaitValue(
        time: Long = 2,
        timeUnit: TimeUnit = TimeUnit.SECONDS,
        onChanged: (() -> Unit)? = null
    ): T {
        var data: T? = null
        val latch = CountDownLatch(1)

        val observer = Observer<T> {
            data = it
            onChanged?.invoke()
            latch.countDown()
        }

        withContext(Dispatchers.Main) {
            observeForever(observer)

            // Don't wait indefinitely if the LiveData is not updated
            if (!latch.await(time, timeUnit)) {
                removeObserver(observer)
            }
        }

        return data!!
    }

}
