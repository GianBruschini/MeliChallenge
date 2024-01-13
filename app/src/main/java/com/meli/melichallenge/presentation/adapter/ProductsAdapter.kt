package com.meli.melichallenge.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meli.melichallenge.data.api.model.response.Product
import com.meli.melichallenge.databinding.ProductItemBinding

class ProductsAdapter: RecyclerView.Adapter<ProductsAdapter.MyViewHolder>() {
    private var listOfProducts = ArrayList<Product>()
    private var mListener: OnItemClickListener? = null


    interface OnItemClickListener {
        fun onitemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ProductItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.productText.text = listOfProducts[position].title
    }

    override fun getItemCount(): Int {
        return listOfProducts.size
    }

    fun setList(listOfProduct: ArrayList<Product>) {
        this.listOfProducts.clear()
        this.listOfProducts = listOfProduct
    }

    inner class MyViewHolder(val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.rlProduct.setOnClickListener {
                if (mListener != null) {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        mListener!!.onitemClick(position)
                    }
                }
            }

        }
    }
}