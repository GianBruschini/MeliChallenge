package com.meli.melichallenge.presentation.search

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.meli.melichallenge.R
import com.meli.melichallenge.databinding.FragmentSearchBinding
import com.meli.melichallenge.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(
    FragmentSearchBinding::inflate,
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        initListeners()
    }


    private fun navigateToProductsFragment() {
        val bundle = Bundle().apply {
            putString("productName", binding.searcher.text.toString())
        }
        val navController = findNavController()
        if (navController.currentDestination?.id == R.id.searchFragment) {
            navController.navigate(R.id.action_searchFragment_to_productsFragment, bundle)
        }
    }


    private fun initListeners() {
        binding.searchBtn.setOnClickListener {
            navigateToProductsFragment()
        }
    }

}