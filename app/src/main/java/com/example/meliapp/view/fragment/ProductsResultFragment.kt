package com.example.meliapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.meliapp.databinding.FragmentProductsResultBinding
import com.example.meliapp.view.ProductsResultAdapter
import com.example.meliapp.view.SearchProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductsResultFragment : Fragment() {

    private var _binding: FragmentProductsResultBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchProductViewModel by viewModels()
    private val productsAdapter = ProductsResultAdapter()
    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        with(binding.rvProductsResult) {
            adapter = productsAdapter
            addItemDecoration(getDividerDecoration())
        }
    }

    private fun getDividerDecoration() =
        DividerItemDecoration(requireContext(), LinearLayout.VERTICAL)


    private fun search(query: String) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchProduct(query).collectLatest {
                productsAdapter.submitData(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}