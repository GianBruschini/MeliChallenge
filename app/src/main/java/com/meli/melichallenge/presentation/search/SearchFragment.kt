package com.meli.melichallenge.presentation.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.meli.melichallenge.R
import com.meli.melichallenge.databinding.FragmentSearchBinding
import com.meli.melichallenge.presentation.base.BaseFragment
import com.meli.melichallenge.util.BundleKeys
import com.meli.melichallenge.util.hideKeyboard
import com.meli.melichallenge.util.showCustomToast
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
            putString(BundleKeys.PRODUCT_NAME, binding.searcher.text.toString())
        }
        val navController = findNavController()
        if (navController.currentDestination?.id == R.id.searchFragment) {
            hideKeyboard()
            navController.navigate(R.id.action_searchFragment_to_productsFragment, bundle)
        }
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initListeners() {
        binding.searchBtn.setOnClickListener {
            if(binding.searcher.text.isNotEmpty()){
                navigateToProductsFragment()
            }else{
                ContextCompat.getDrawable(requireContext(), R.drawable.rounded_gradient_error)
                    ?.let { drawable ->
                        Toast(requireContext()).showCustomToast(
                            getString(R.string.search_warning_empty_text),
                            drawable,
                            requireActivity()
                        )
                    }

            }

        }
    }

}