package com.example.meliapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.meliapp.data.api.MeLiApi
import com.example.meliapp.data.datasource.NETWORK_PAGE_SIZE
import com.example.meliapp.data.datasource.ProductsDataSource
import com.example.meliapp.data.model.Results
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val service: MeLiApi
) : ProductsRepository {

    override fun getProductsFromSearch(query: String): Flow<PagingData<Results>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ProductsDataSource(service, query) }
        ).flow
    }
}