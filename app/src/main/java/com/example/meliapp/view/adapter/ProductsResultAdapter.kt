package com.example.meliapp.view.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.meliapp.data.model.Results
import com.example.meliapp.databinding.ProductItemBinding
import com.example.meliapp.utils.formatAsCurrency
import com.example.meliapp.utils.getInflater

typealias OnClickItem = (Results?) -> Unit

class ProductsResultAdapter(private val onClickItem: OnClickItem) :
    PagingDataAdapter<Results, ProductsResultAdapter.ProductsResultViewHolder>(PRODUCT_DIFF_CALLBACK) {

    class ProductsResultViewHolder(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Results?, onClickItem: OnClickItem) {
            with(binding) {
                root.setOnClickListener {
                    onClickItem(item)
                }
                productTitle.text = item?.title
                productImage.load(item?.thumbnail)
                productPrice.text = item?.price?.formatAsCurrency()
            }
        }
    }

    override fun onBindViewHolder(holder: ProductsResultViewHolder, position: Int) {
        holder.bind(getItem(position), onClickItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsResultViewHolder {
        val binding = ProductItemBinding.inflate(parent.getInflater(), parent, false)
        return ProductsResultViewHolder(binding)
    }

    companion object {
        private val PRODUCT_DIFF_CALLBACK = object : DiffUtil.ItemCallback<Results>() {
            override fun areItemsTheSame(oldItem: Results, newItem: Results): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Results, newItem: Results): Boolean {
                return oldItem == newItem
            }

        }
    }
}