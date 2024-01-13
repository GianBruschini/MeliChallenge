package com.meli.melichallenge.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.meli.melichallenge.data.api.model.response.Product
import com.meli.melichallenge.databinding.ProductItemBinding

class ProductsAdapter: RecyclerView.Adapter<ProductsAdapter.MyViewHolder>() {
    private var context: Context? = null
    private var listOfProducts = ArrayList<Product>()
    private var mListener: OnItemClickListener? = null


    interface OnItemClickListener {
        fun onitemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        this.context = parent.context
        return MyViewHolder(
            ProductItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val url = listOfProducts[position].thumbnail
        context?.let { Glide.with(it).load("https://http2.mlstatic.com/D_738121-MLU72537283306_112023-I.jpg").into(holder.binding.productImage) }
        holder.binding.productTitle.text = listOfProducts[position].title
        holder.binding.productPrice.text = listOfProducts[position].price.toString()

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