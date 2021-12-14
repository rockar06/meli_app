package com.example.meliapp.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.meliapp.data.api.MeLiApi
import com.example.meliapp.data.model.Results

const val NETWORK_PAGE_SIZE = 15
const val MELI_STARTING_PAGE_INDEX = 0

class ProductsDataSource(
    private val service: MeLiApi,
    private val query: String
) : PagingSource<Int, Results>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Results> {
        val position = params.key ?: MELI_STARTING_PAGE_INDEX
        return try {
            val response = service.searchForProducts(query, position, params.loadSize)
            val products = response.results
            val nextKey = if (products.isEmpty()) {
                null
            } else {
                position + params.loadSize
            }
            val prevKey = if (position == MELI_STARTING_PAGE_INDEX) {
                null
            } else {
                position - NETWORK_PAGE_SIZE
            }
            LoadResult.Page(
                data = products,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Results>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
                ?: state.closestPageToPosition(anchorPosition)?.nextKey
        }
    }
}