package com.meli.melichallenge.presentation.detailproduct

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meli.melichallenge.R
import com.meli.melichallenge.data.api.model.response.Product
import com.meli.melichallenge.data.api.model.response.ProductDetail
import com.meli.melichallenge.domain.usecase.product.GetProductDetailUseCase
import com.meli.melichallenge.domain.usecase.product.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailProductViewModel @Inject constructor(
    private val getDetailProductDetailUseCase: GetProductDetailUseCase,
) : ViewModel() {

    private var _productsDetail = MutableLiveData<ProductDetail?>(null)
    val productsDetail: MutableLiveData<ProductDetail?> get() = _productsDetail

   fun getProductById(idProduct: String){
       viewModelScope.launch {
           val resultQuery = getDetailProductDetailUseCase.getProductDetail(idProduct)
           if (resultQuery.isSuccessful) {
               handleSuccessfulFetch(resultQuery.body())
           } else {
               handleErrorFetch()
           }
       }
   }

    private fun handleErrorFetch() {

    }

    private fun handleSuccessfulFetch(body: ProductDetail?) {
        _productsDetail.value = body
    }
}