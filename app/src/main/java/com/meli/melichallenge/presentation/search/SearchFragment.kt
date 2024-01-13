package com.meli.melichallenge.presentation.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.meli.melichallenge.R
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
            when(loading){
                true->{
                    binding.progressBar.visibility=View.VISIBLE
                }
                false -> {
                    binding.progressBar.visibility=View.GONE
                }
            }
    }

    private fun handleUserMessage(userMessage: BindingString?) {
        userMessage?.let {
            handleError(it)
        }
    }

    private fun handleGetProducts(productsFetched: List<Product>?) {
        productsFetched?.let {
            val bundle = Bundle().apply {
                putSerializable("productsList", ArrayList(it))
            }
            navigateToProductsFragment(bundle)
        }

    }

    private fun navigateToProductsFragment(bundle: Bundle) {
        val navController = findNavController()
        if (navController.currentDestination?.id == R.id.searchFragment) {
            navController.navigate(R.id.action_searchFragment_to_productsFragment, bundle)
        }
    }


    private fun initListeners() {
        binding.searchBtn.setOnClickListener {
            searchViewModel.getProducts(binding.searcher.text.toString())
        }
    }

}