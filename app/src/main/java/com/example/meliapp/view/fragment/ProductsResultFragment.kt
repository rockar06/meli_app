package com.example.meliapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.meliapp.data.model.Results
import com.example.meliapp.databinding.FragmentProductsResultBinding
import com.example.meliapp.view.adapter.ProductsLoadStateAdapter
import com.example.meliapp.view.adapter.ProductsResultAdapter
import com.example.meliapp.view.viewmodel.SearchProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductsResultFragment : Fragment(), SearchProduct {

    private var _binding: FragmentProductsResultBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchProductViewModel by viewModels()
    private lateinit var navController: NavController
    private var searchJob: Job? = null
    private val productsAdapter = ProductsResultAdapter { item ->
        openProductDetailPage(item)
    }

    private fun openProductDetailPage(item: Results?) {
        val action =
            ProductsResultFragmentDirections.actionProductsResultFragmentToProductDetailPageFragment(
                item
            )
        findNavController().navigate(action)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupNavController()
        setupRecyclerView()
        setupAdapter()
        setupRetryButton()
        showDefaultView()
    }

    private fun setupNavController() {
        navController = Navigation.findNavController(binding.root)
    }

    private fun setupRecyclerView() {
        with(binding.rvProductsResult) {
            adapter = productsAdapter.withLoadStateFooter(ProductsLoadStateAdapter {
                productsAdapter.retry()
            })
            addItemDecoration(getDividerDecoration())
        }
    }

    private fun setupAdapter() {
        productsAdapter.addLoadStateListener { loadState ->
            val isEmpty =
                loadState.refresh is LoadState.NotLoading && productsAdapter.itemCount == 0
            showEmptyList(isEmpty)
            binding.rvProductsResult.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            binding.errorGroup.isVisible = loadState.source.refresh is LoadState.Error
        }
    }

    private fun showEmptyList(empty: Boolean) {
        binding.rvProductsResult.isVisible = empty.not()
        binding.errorGroup.isVisible = empty.not()
        binding.progressBar.isVisible = empty.not()
        binding.emptyListGroup.isVisible = empty
    }

    private fun setupRetryButton() {
        binding.retryButton.setOnClickListener { productsAdapter.retry() }
    }

    private fun showDefaultView() {
        binding.startSearchGroup.isVisible = productsAdapter.itemCount == 0
    }

    private fun getDividerDecoration() =
        DividerItemDecoration(requireContext(), LinearLayout.VERTICAL)


    private fun search(query: String) {
        searchJob?.cancel()
        binding.startSearchGroup.isVisible = false
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

    override fun onNewSearch(query: String) {
        search(query)
    }
}