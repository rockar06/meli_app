package com.example.meliapp.view.adapter

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.meliapp.R
import com.example.meliapp.databinding.ProductsLoadStateFooterViewItemBinding
import com.example.meliapp.utils.getInflater

typealias OnRetryClick = () -> Unit

class ProductsLoadStateAdapter(private val retry: OnRetryClick) :
    LoadStateAdapter<ProductsLoadStateAdapter.ProductsLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: ProductsLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ProductsLoadStateViewHolder {
        return ProductsLoadStateViewHolder.create(parent, retry)
    }

    class ProductsLoadStateViewHolder(
        private val binding: ProductsLoadStateFooterViewItemBinding,
        retry: OnRetryClick
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryButton.setOnClickListener { retry() }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.tvErrorMessage.setText(R.string.unexpected_error_loading_data)
            }
            binding.retryGroup.isVisible = loadState is LoadState.Error
            binding.progressCircular.isVisible = loadState is LoadState.Loading
        }

        companion object {
            fun create(parent: ViewGroup, retry: OnRetryClick): ProductsLoadStateViewHolder {
                val binding = ProductsLoadStateFooterViewItemBinding.inflate(
                    parent.getInflater(),
                    parent,
                    false
                )
                return ProductsLoadStateViewHolder(binding, retry)
            }
        }
    }
}

