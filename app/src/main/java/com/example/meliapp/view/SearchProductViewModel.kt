package com.example.meliapp.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.meliapp.data.model.Results
import com.example.meliapp.data.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchProductViewModel @Inject constructor(
    private val repository: ProductsRepository
): ViewModel() {

    private var currentQueryValue: String? = null
    private var currentSearchResult: Flow<PagingData<Results>>? = null

    fun searchProduct(queryString: String): Flow<PagingData<Results>> {
        val lastResult = currentSearchResult
        if (queryString == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = queryString
        val newResult = repository.getProductsFromSearch(queryString).cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}