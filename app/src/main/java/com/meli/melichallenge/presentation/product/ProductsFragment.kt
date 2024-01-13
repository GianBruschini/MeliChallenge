package com.meli.melichallenge.presentation.product


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.meli.melichallenge.data.api.model.response.Product
import com.meli.melichallenge.databinding.FragmentProductsBinding
import com.meli.melichallenge.presentation.adapter.ProductsAdapter
import com.meli.melichallenge.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment : BaseFragment<FragmentProductsBinding>(
    FragmentProductsBinding::inflate,
),ProductsAdapter.OnItemClickListener {
    private var productsList: List<Product>? = null
    private val productsAdapter = ProductsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            productsList = savedInstanceState.getSerializable("productsList") as? ArrayList<Product>
        }else{
            productsList = arguments?.getSerializable("productsList") as? ArrayList<Product>
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        productsList?.let { fillProductsRv(it) }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fillProductsRv(productsList: List<Product>) {
        val arrayListOfObras = ArrayList(productsList)
        productsAdapter.setList(arrayListOfObras)
        productsAdapter.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.productsRv.layoutManager = linearLayoutManager
        binding.productsRv.setHasFixedSize(true)
        binding.productsRv.adapter = productsAdapter
        binding.productsRv.adapter?.notifyDataSetChanged()
        productsAdapter.setOnItemClickListener(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("productsList", ArrayList(productsList))
    }

    override fun onitemClick(position: Int) {

    }

}