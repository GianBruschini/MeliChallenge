package com.meli.melichallenge.presentation.search

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meli.melichallenge.R
import com.meli.melichallenge.data.api.model.response.Product
import com.meli.melichallenge.domain.usecase.product.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val PAGE_SIZE = 50

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private var _productsList = MutableLiveData<List<Product>>()
    val productsList: LiveData<List<Product>> get() = _productsList


    private var _isLoaderVisible = MutableLiveData(false)
    val isLoaderVisible: LiveData<Boolean> get() = _isLoaderVisible

    var _errorEvent = MutableLiveData<String>()
    val errorEvent: LiveData<String> get() = _errorEvent

    private var _isEmptySearchVisible = MutableLiveData(false)
    val isEmptySearchVisible: LiveData<Boolean> get() = _isEmptySearchVisible


    private fun getOffset() = _productsList.value?.size ?: 0

    fun getProducts(aProduct: String) {
        _isLoaderVisible.value = true
        viewModelScope.launch {
            val resultQuery = getProductsUseCase.searchProducts(aProduct, getOffset(), PAGE_SIZE)
            if (resultQuery.isSuccessful) {
                handleSuccessfulSearch(resultQuery.body()?.results!!)
            } else {
                handleErrorSearch()
            }
            _isLoaderVisible.value = false
        }

    }

    private fun handleSuccessfulSearch(results: List<Product>) {
        _productsList.value = results
    }

    private fun handleErrorSearch() {
        _isLoaderVisible.value = false
        _errorEvent.value = context.resources.getString(R.string.error_products_search)
        _isEmptySearchVisible.value = _productsList.value.isNullOrEmpty()
    }

}

