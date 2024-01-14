package com.meli.melichallenge.presentation.detailproduct

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.meli.melichallenge.databinding.FragmentDetailProductsBinding
import com.meli.melichallenge.databinding.FragmentProductsBinding
import com.meli.melichallenge.presentation.base.BaseFragment
import com.meli.melichallenge.presentation.product.ProductViewModel
import com.meli.melichallenge.util.BundleKeys
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailProductFragment : BaseFragment<FragmentDetailProductsBinding>(
    FragmentDetailProductsBinding::inflate,
) {
    private var productId: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productId = arguments?.getString(BundleKeys.PRODUCT_ID).toString()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        fillUi()
        initOnClicks()
    }

    private fun initOnClicks() {
        binding.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun fillUi() {
        context?.let {
            Glide.with(it)
                .load(arguments?.getString(BundleKeys.PRODUCT_IMAGE).toString())
                .into(binding.productImage)
        }

        binding.productTitle.text = arguments?.getString(BundleKeys.PRODUCT_TITLE).toString()
        val permalinkText = "Para tener más información del producto, consulta:\n\n ${
            arguments?.getString(BundleKeys.PRODUCT_PERMALINK).toString()}"
        binding.productPermalink.text = permalinkText
        binding.productPermalink.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(arguments?.getString("productPermaLink").toString()))
            startActivity(intent)
        }
    }


}
