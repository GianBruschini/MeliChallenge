package com.meli.melichallenge.presentation.detailproduct

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.meli.melichallenge.R
import com.meli.melichallenge.data.api.model.response.ItemResponse
import com.meli.melichallenge.databinding.FragmentDetailProductsBinding
import com.meli.melichallenge.databinding.FragmentProductsBinding
import com.meli.melichallenge.presentation.base.BaseFragment
import com.meli.melichallenge.presentation.product.ProductViewModel
import com.meli.melichallenge.util.BundleKeys
import com.meli.melichallenge.util.showCustomToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailProductFragment : BaseFragment<FragmentDetailProductsBinding>(
    FragmentDetailProductsBinding::inflate,
) {
    private var productId: String = ""
    private val detailProductViewModel: DetailProductViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productId = arguments?.getString(BundleKeys.PRODUCT_ID).toString()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        initObservers()
        initOnClicks()
    }

    private fun initObservers() {
        detailProductViewModel.isLoaderVisible.observe(viewLifecycleOwner) {
            handleLoading(it)
        }
        detailProductViewModel.getItem.observe(viewLifecycleOwner) { item->
            item?.let { it1 -> handleItemFetched(it1) }
        }

        detailProductViewModel.errorEvent.observe(viewLifecycleOwner) { it->
            handleError(it)
        }
        detailProductViewModel.fetchItem(productId)
    }

    private fun handleError(messageToShow: String?) {
        ContextCompat.getDrawable(requireContext(), R.drawable.rounded_gradient_error)
            ?.let { drawable ->
                Toast(requireContext()).showCustomToast(
                    messageToShow.toString(),
                    drawable,
                    requireActivity()
                )
            }
    }

    private fun handleItemFetched(item: ItemResponse) {
        fillUiWith(item)
    }

    private fun handleLoading(loading: Boolean?) {
        when(loading){
            true -> {
                binding.progressBar.visibility=View.VISIBLE
            }
            false -> {
                binding.progressBar.visibility=View.GONE
            }
            null -> {

            }
        }
    }

    private fun initOnClicks() {
        binding.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun fillUiWith(item: ItemResponse) {
        context?.let {
            Glide.with(it)
                .load(item.thumbnail)
                .into(binding.productImage)
        }

        binding.productTitle.text = item.title
        val permalinkInfo = getString(R.string.detail_permalink_info)
        val permalinkText = "$permalinkInfo\n\n${arguments?.getString(BundleKeys.PRODUCT_PERMALINK).orEmpty()}"
        binding.productPermalink.text = permalinkText

        binding.productPermalink.setOnClickListener {
            val permalinkUrl = arguments?.getString(BundleKeys.PRODUCT_PERMALINK).orEmpty()
            openUrlInBrowser(permalinkUrl)
        }
    }

    private fun openUrlInBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }




}
