package com.meli.melichallenge.presentation.product


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.meli.melichallenge.data.api.model.response.Product
import com.meli.melichallenge.databinding.FragmentProductsBinding
import com.meli.melichallenge.presentation.adapter.ProductsAdapter
import com.meli.melichallenge.presentation.base.BaseFragment
import com.meli.melichallenge.presentation.search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment : BaseFragment<FragmentProductsBinding>(
    FragmentProductsBinding::inflate,
), ProductsAdapter.OnItemClickListener {
    private var listOfProducts: ArrayList<Product>? = null
    private var productName = ""
    private val searchViewModel: SearchViewModel by viewModels()
    private var productsAdapter: ProductsAdapter = ProductsAdapter()
    private var visibleItemCount = 0
    private var totalItemCount = 0
    private var pastVisibleItems = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productName = arguments?.getString("productName").toString()
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
                        pastVisibleItems = (it as LinearLayoutManager).findFirstVisibleItemPosition()
                    }
                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        searchViewModel.getProducts(productName)
                    }
                }
            }
        })

        // Observa cambios en la lista de productos
        searchViewModel.productsList.observe(viewLifecycleOwner) { products ->
            products?.let {
                fillProductsRv(it)
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
        initRecyclerView()
        initObservers()
        executeObservers()
    }

    private fun executeObservers() {
        searchViewModel.getProducts(productName)
    }

    @SuppressLint("UseCompatLoadingForDrawables", "NotifyDataSetChanged")
    private fun fillProductsRv(productsList: List<Product>) {
        val arrayListOfProducts = ArrayList(productsList)
        productsAdapter.setList(arrayListOfProducts)
        productsAdapter.notifyDataSetChanged()
    }

    private fun initObservers() {
        searchViewModel.isLoaderVisible.observe(viewLifecycleOwner) {
            handleLoading(it)
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

    }


}