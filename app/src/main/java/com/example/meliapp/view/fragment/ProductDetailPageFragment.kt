package com.example.meliapp.view.fragment

import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.buildSpannedString
import androidx.core.text.inSpans
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.meliapp.R
import com.example.meliapp.data.model.Installments
import com.example.meliapp.data.model.Results
import com.example.meliapp.databinding.FragmentProductDetailBinding
import com.example.meliapp.utils.formatAsCurrency

class ProductDetailPageFragment : Fragment() {

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!
    private val arguments: ProductDetailPageFragmentArgs by navArgs()
    private val productDetail: Results? by lazy { arguments.productDetail }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewWithProductDetails()
    }

    private fun setupViewWithProductDetails() {
        productDetail?.let {
            fillView(it)
        }
    }

    private fun fillView(product: Results) {
        with(binding) {
            productCondition.text = getProductConditionText(product.condition)
            productSoldQuantity.text = getString(R.string.sold_quantity, product.soldQuantity)
            productTitle.text = product.title
            productImage.load(product.thumbnail)
            productPrice.text = product.price.formatAsCurrency()
            productPlan.text = getFormattedPlan(product.installments)
        }
    }

    private fun getFormattedPlan(installments: Installments): CharSequence {
        return if (installments.rate == 0.toDouble()) {
            buildSpannedString {
                append(getString(R.string.product_plan_prefix))
                inSpans(
                    ForegroundColorSpan(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.green_color
                        )
                    )
                ) {
                    append(
                        getString(
                            R.string.product_plan_detail,
                            installments.quantity,
                            installments.amount
                        )
                    )
                }
            }
        } else {
            getString(
                R.string.financial_plan,
                installments.quantity,
                installments.amount
            )
        }
    }

    private fun getProductConditionText(condition: String) = when(condition) {
        NEW_PRODUCT -> getString(R.string.new_product)
        else -> getString(R.string.used_product)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val NEW_PRODUCT = "new"
    }
}