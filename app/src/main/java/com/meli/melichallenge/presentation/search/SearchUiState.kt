package com.meli.melichallenge.presentation.search

import com.meli.melichallenge.data.api.model.response.Product
import com.meli.melichallenge.util.BindingString

data class SearchUiState(
    val loading: Boolean = false,
    val userMessage: BindingString? = null,
    val productsFetched: List<Product>? = null
) {

}