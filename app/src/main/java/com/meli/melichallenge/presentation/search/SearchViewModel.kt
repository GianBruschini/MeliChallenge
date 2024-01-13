package com.meli.melichallenge.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meli.melichallenge.data.api.model.response.Product
import com.meli.melichallenge.domain.model.ResultValue
import com.meli.melichallenge.domain.usecase.product.GetProductsUseCase
import com.meli.melichallenge.util.errorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()
    private var fetchJob: Job? = null

    fun getProducts(aProduct: String) {
        setLoading(true)
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            val result = getProductsUseCase(aProduct)
            handleResult(result)
            setLoading(false)
        }
    }

    private fun handleResult(result: ResultValue<List<Product>>) {
        when (result) {
            is ResultValue.Success -> {
                _uiState.update { it.copy(productsFetched = result.data) }
            }
            is ResultValue.Error -> {
                _uiState.update { it.copy(userMessage = result.exception.errorMessage()) }
            }
        }
    }

    private fun setLoading(loading: Boolean) {
        _uiState.update { it.copy(loading = loading) }
    }
}

