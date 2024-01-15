package com.meli.melichallenge.presentation.detailproduct

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.meli.melichallenge.data.api.model.response.AddressProperty
import com.meli.melichallenge.data.api.model.response.AttributeResponse
import com.meli.melichallenge.data.api.model.response.ItemResponse
import com.meli.melichallenge.data.api.model.response.PictureResponse
import com.meli.melichallenge.data.api.model.response.SellerAddressResponse
import com.meli.melichallenge.domain.usecase.product.GetProductDetailUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
class DetailProductViewModelTest {

    @RelaxedMockK
    private lateinit var getProductDetailUseCase: GetProductDetailUseCase
    @RelaxedMockK
    private lateinit var detailProductViewModel: DetailProductViewModel
    private lateinit var context: Context

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        context = mockk(relaxed = true)
        detailProductViewModel = DetailProductViewModel(getProductDetailUseCase, context)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when fetchItem is called, add result to LiveData`() =
        runTest {
            // Given
            val mockItemResponse = ItemResponse(
                "1", "Product 1", 10.0f, "USD", "New",
                "thumbnail1", arrayListOf(PictureResponse("1", "url1", "size1", "maxSize1")),
                10, SellerAddressResponse(AddressProperty("City1"), AddressProperty("State1")),
                arrayListOf(AttributeResponse("1", "Attribute1", "Value1"))
            )

            coEvery {
                getProductDetailUseCase.getItem(any())
            } returns Response.success(mockItemResponse)

            // When
            detailProductViewModel.fetchItem("itemId")

            // Then
            val observedItem = detailProductViewModel.getItem.getOrAwaitValue()


            assertEquals(mockItemResponse, observedItem)

            assertEquals(mockItemResponse, detailProductViewModel.getItem.value)
        }

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
            if (!latch.await(time, timeUnit)) {
                removeObserver(observer)
            }
        }

        return data!!
    }
}
