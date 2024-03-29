package com.meli.melichallenge.presentation.product


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.meli.melichallenge.R
import com.meli.melichallenge.data.api.model.response.Product
import com.meli.melichallenge.databinding.FragmentProductsBinding
import com.meli.melichallenge.presentation.adapter.ProductsAdapter
import com.meli.melichallenge.presentation.base.BaseFragment
import com.meli.melichallenge.util.BundleKeys
import com.meli.melichallenge.util.showCustomToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment : BaseFragment<FragmentProductsBinding>(
    FragmentProductsBinding::inflate,
), ProductsAdapter.OnItemClickListener {
    private var listOfProducts: ArrayList<Product>? = null
    private var productName = ""
    private val productViewModel: ProductViewModel by viewModels()
    private var productsAdapter: ProductsAdapter = ProductsAdapter()
    private var visibleItemCount = 0
    private var totalItemCount = 0
    private var pastVisibleItems = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productName = arguments?.getString(BundleKeys.PRODUCT_NAME).toString()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        observeScrollEvents()
    }

    private fun observeScrollEvents() {
        binding.productsRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    binding.productsRv.layoutManager?.let {
                        visibleItemCount = it.childCount
                        totalItemCount = it.itemCount
                        pastVisibleItems =
                            (it as LinearLayoutManager).findFirstVisibleItemPosition()
                    }
                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        productViewModel.getProducts(productName)
                    }
                }
            }
        })

        productViewModel.productsList.observe(viewLifecycleOwner) { products ->
            if (products?.isEmpty() == true) {
                binding.noResultsTxt.visibility = View.VISIBLE
            } else {
                binding.noResultsTxt.visibility = View.GONE
                products?.let {
                    this.listOfProducts = it as ArrayList<Product>
                    fillProductsRv(it)
                }
            }

        }
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun initRecyclerView() {
        val linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.productsRv.layoutManager = linearLayoutManager
        binding.productsRv.setHasFixedSize(true)
        binding.productsRv.adapter = productsAdapter
        binding.productsRv.adapter?.notifyDataSetChanged()
        productsAdapter.setOnItemClickListener(this)
    }


    private fun initUi() {
        initOnClicks()
        initRecyclerView()
        initObservers()
        executeObservers()
    }

    private fun initOnClicks() {
        binding.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun executeObservers() {
        productViewModel.getProducts(productName)
    }

    @SuppressLint("UseCompatLoadingForDrawables", "NotifyDataSetChanged")
    private fun fillProductsRv(productsList: List<Product>) {
        val arrayListOfProducts = ArrayList(productsList)
        productsAdapter.setList(arrayListOfProducts)
        productsAdapter.notifyDataSetChanged()
    }

    private fun initObservers() {
        productViewModel.isLoaderVisible.observe(viewLifecycleOwner) {
            handleLoading(it)
        }
        productViewModel.errorEvent.observe(viewLifecycleOwner) {
            handleError(it)
        }

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

    private fun handleLoading(loading: Boolean) {
        when (loading) {
            true -> {
                binding.progressBar.visibility = View.VISIBLE
            }

            false -> {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    override fun onitemClick(position: Int) {
        listOfProducts?.get(position)
            ?.let { navigateToProductDetailFragment(it.id,it.permalink) }
    }

    private fun navigateToProductDetailFragment(
        productId: String,
        permalink: String,
    ) {
        val bundle = Bundle().apply {
            putString(BundleKeys.PRODUCT_ID, productId)
            putString(BundleKeys.PRODUCT_PERMALINK, permalink)
        }
        val navController = findNavController()
        navController.navigate(R.id.action_productsFragment_to_detailProductsFragment, bundle)
    }

}