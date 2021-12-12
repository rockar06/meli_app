package com.example.meliapp.data.datasource

import androidx.paging.PagingSource
import com.example.meliapp.data.api.MeLiApi
import com.example.meliapp.data.model.Paging
import com.example.meliapp.data.model.Results
import com.example.meliapp.data.model.SearchResult
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.HttpException

@ExperimentalCoroutinesApi
class ProductsDataSourceTest {

    private val mockMeLiApi: MeLiApi = mock()
    private val mockQuery = "query"
    private val mockResults: Results = mock()
    private val mockedListOfResults = listOf(mockResults, mockResults, mockResults)

    @Test
    fun load_onSuccessfulLoadOfItemKeyedData_returnsPage() = runBlockingTest {
        mockApiResponse()
        val pagingSource = ProductsDataSource(mockMeLiApi, mockQuery)
        assertThat(
            pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 60,
                    placeholdersEnabled = false
                )
            )
        ).isEqualTo(
            PagingSource.LoadResult.Page(
                data = mockedListOfResults,
                prevKey = null,
                nextKey = 60
            )
        )
    }

    private fun mockApiResponse() = runBlockingTest {
        whenever(mockMeLiApi.searchForProducts(any(), any(), any())).thenReturn(
            SearchResult(
                paging = Paging(100, 100, 0, 60),
                results = mockedListOfResults
            )
        )
    }

    @Test
    fun load_onSuccessfulLoadOfItemKeyedData_returnsNullNextKey() = runBlockingTest {
        mockApiResponseNoMoreResults()
        val pagingSource = ProductsDataSource(mockMeLiApi, mockQuery)
        assertThat(
            pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 60,
                    placeholdersEnabled = false
                )
            )
        ).isEqualTo(
            PagingSource.LoadResult.Page(
                data = emptyList(),
                prevKey = null,
                nextKey = null
            )
        )
    }

    private fun mockApiResponseNoMoreResults() = runBlockingTest {
        whenever(mockMeLiApi.searchForProducts(any(), any(), any())).thenReturn(
            SearchResult(
                paging = Paging(100, 100, 0, 60),
                results = emptyList()
            )
        )
    }

    @Test
    fun load_onErrorLoadingItems_returnPageError() = runBlockingTest {
        mockApiResponseThrowsError()
        val pagingSource = ProductsDataSource(mockMeLiApi, mockQuery)
        assertThat(
            pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 60,
                    placeholdersEnabled = false
                )
            )
        ).isInstanceOf(PagingSource.LoadResult.Error::class.java)
    }

    private fun mockApiResponseThrowsError() = runBlockingTest {
        whenever(
            mockMeLiApi.searchForProducts(
                any(),
                any(),
                any()
            )
        ).thenThrow(HttpException::class.java)
    }
}