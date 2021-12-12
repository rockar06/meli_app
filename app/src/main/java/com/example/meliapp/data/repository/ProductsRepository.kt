package com.example.meliapp.data.repository

import androidx.paging.PagingData
import com.example.meliapp.data.model.Results
import kotlinx.coroutines.flow.Flow
import com.example.meliapp.data.api.MeLiApi

interface ProductsRepository {

    /**
     * Load all results returned by [MeLiApi] using the given [query]
     * @return [Results] inside a [PagingData] object in the stream of a [Flow]
     */
    fun getProductsFromSearch(query: String): Flow<PagingData<Results>>
}