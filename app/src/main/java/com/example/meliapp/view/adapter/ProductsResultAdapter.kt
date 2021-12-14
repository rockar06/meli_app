package com.example.meliapp.view.adapter

import android.content.Context
import android.graphics.Typeface
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.buildSpannedString
import androidx.core.text.inSpans
import androidx.core.view.isInvisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.meliapp.R
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
                productFinancialPlan.text = getFinancialPlan(binding.root.context, item)
                productShipment.isInvisible = item?.shipping?.freeShipping?.not() ?: true
            }
        }

        private fun getFinancialPlan(context: Context, item: Results?): CharSequence {
            val installments = item?.installments
            return if (installments?.rate == 0.toDouble()) {
                buildSpannedString {
                    inSpans(
                        ForegroundColorSpan(
                            ContextCompat.getColor(
                                context,
                                R.color.green_color
                            )
                        )
                    ) {
                        append(
                            context.getString(
                                R.string.financial_plan_msi,
                                installments.quantity,
                                installments.amount
                            )
                        )
                    }
                }
            } else {
                context.getString(
                    R.string.financial_plan,
                    installments?.quantity,
                    installments?.amount
                )
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