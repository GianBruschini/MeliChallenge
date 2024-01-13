package com.meli.melichallenge.presentation.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.meli.melichallenge.data.api.model.response.Product
import com.meli.melichallenge.databinding.FragmentSearchBinding
import com.meli.melichallenge.presentation.base.BaseFragment
import com.meli.melichallenge.util.BindingString
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(
    FragmentSearchBinding::inflate,
) {

    private val searchViewModel: SearchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        initListeners()
        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launchWhenStarted {
            searchViewModel.uiState.collect { state ->
                handleGetProducts(state.productsFetched)
                handleLoading(state.loading)
                handleUserMessage(state.userMessage)
            }
        }
    }

    private fun handleLoading(loading: Boolean) {

    }

    private fun handleUserMessage(userMessage: BindingString?) {
        userMessage?.let {
            handleError(it)
        }
    }

    private fun handleGetProducts(productsFetched: List<Product>?) {

    }

    private fun initListeners() {
        binding.searchBtn.setOnClickListener {
            searchViewModel.getProducts(binding.searcher.text.toString())
        }
    }

}