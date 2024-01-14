package com.meli.melichallenge.presentation.detailproduct

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.meli.melichallenge.databinding.FragmentDetailProductsBinding
import com.meli.melichallenge.databinding.FragmentProductsBinding
import com.meli.melichallenge.presentation.base.BaseFragment
import com.meli.melichallenge.presentation.product.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailProductFragment : BaseFragment<FragmentDetailProductsBinding>(
    FragmentDetailProductsBinding::inflate,
) {
    private var productId: String = ""
    private val productDetailViewModel: DetailProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productId = arguments?.getString("productId").toString()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        initObservers()
        executeObservers()
    }

    private fun executeObservers() {
        productDetailViewModel.getProductById(productId)
    }

    private fun initObservers() {
        productDetailViewModel.productsDetail.observe(viewLifecycleOwner) {
           it
        }
    }
}