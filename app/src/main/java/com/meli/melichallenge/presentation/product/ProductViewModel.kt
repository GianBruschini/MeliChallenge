package com.meli.melichallenge.presentation.product

import android.content.Context
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

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private var _productsList = MutableLiveData<List<Product>?>(null)
    val productsList: LiveData<List<Product>?> get() = _productsList

    private var _isLoaderVisible = MutableLiveData(false)
    val isLoaderVisible: LiveData<Boolean> get() = _isLoaderVisible

    private var _errorEvent = MutableLiveData<String>()
    val errorEvent: LiveData<String> get() = _errorEvent

    private var _isEmptySearchVisible = MutableLiveData(false)
    val isEmptySearchVisible: LiveData<Boolean> get() = _isEmptySearchVisible

    private var _hasMoreItems = MutableLiveData(true)
    val hasMoreItems: LiveData<Boolean> get() = _hasMoreItems

    private var currentPage = 1
    private val PAGE_SIZE = 20

    fun getProducts(aProduct: String) {
        if (_hasMoreItems.value == false) return // no more elementss to load
        _isLoaderVisible.value = true
        viewModelScope.launch {
            val resultQuery = getProductsUseCase.searchProducts(aProduct, currentPage, PAGE_SIZE)
            if (resultQuery.isSuccessful) {
                handleSuccessfulSearch(resultQuery.body()?.results!!)
            } else {
                handleErrorSearch()
            }
            _isLoaderVisible.value = false
        }
    }

    private fun handleSuccessfulSearch(results: List<Product>) {
        if (_productsList.value == null) {
            _productsList.value = results
        } else {
            val currentList = _productsList.value!!.toMutableList()
            currentList.addAll(results)
            _productsList.value = currentList
        }

        _hasMoreItems.value = results.size == PAGE_SIZE
        currentPage++
    }

    private fun handleErrorSearch() {
        _isLoaderVisible.value = false
        _errorEvent.value = context.resources.getString(R.string.product_error_search)
        _isEmptySearchVisible.value = _productsList.value.isNullOrEmpty()
    }
}