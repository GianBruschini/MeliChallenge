package com.meli.melichallenge.presentation.detailproduct

import android.content.ClipData.Item
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meli.melichallenge.R
import com.meli.melichallenge.data.api.model.response.ItemResponse
import com.meli.melichallenge.data.api.model.response.Product
import com.meli.melichallenge.domain.usecase.product.GetProductDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailProductViewModel @Inject constructor(
    private val getProductDetailUseCase: GetProductDetailUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private var _getItem = MutableLiveData<ItemResponse?>(null)
    val getItem: MutableLiveData<ItemResponse?> get() = _getItem
    private var _isLoaderVisible = MutableLiveData(false)
    val isLoaderVisible: LiveData<Boolean> get() = _isLoaderVisible
    private var _errorEvent = MutableLiveData<String>()
    val errorEvent: LiveData<String> get() = _errorEvent


    fun fetchItem(anItemId:String){
        _isLoaderVisible.value = true
        viewModelScope.launch {
            val resultQuery = getProductDetailUseCase.getItem(anItemId)
            if (resultQuery.isSuccessful) {
                handleSuccessfulSearch(resultQuery.body())
            } else {
                handleErrorSearch()
            }
            _isLoaderVisible.value = false
        }
    }

    private fun handleErrorSearch() {
        _errorEvent.value = context.resources.getString(R.string.detail_error_item)
    }

    private fun handleSuccessfulSearch(body: ItemResponse?) {
        _getItem.value = body
    }


}