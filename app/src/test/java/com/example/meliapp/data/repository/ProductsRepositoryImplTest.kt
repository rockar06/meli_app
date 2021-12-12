package com.example.meliapp.data.repository

import androidx.paging.PagingData
import com.example.meliapp.data.api.MeLiApi
import com.example.meliapp.data.model.Paging
import com.example.meliapp.data.model.Results
import com.example.meliapp.data.model.SearchResult
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class ProductsRepositoryImplTest {

    private val mockedService: MeLiApi = mock()
    private val mockQuery = "query"
    private val mockResults: Results = mock()
    private val mockedListOfResults = listOf(mockResults, mockResults, mockResults)
    private val systemUnderTest = ProductsRepositoryImpl(mockedService)

    @Test
    fun getProductsFromSearch_onSuccessfullyReturnResults_returnProductsUsingFlow() =
        runBlockingTest {
            mockApiResponse()
            val result = systemUnderTest.getProductsFromSearch(mockQuery).first()
            assertThat(result).isInstanceOf(PagingData::class.java)
        }

    private fun mockApiResponse() = runBlockingTest {
        whenever(mockedService.searchForProducts(any(), any(), any())).thenReturn(
            SearchResult(
                paging = Paging(100, 100, 0, 60),
                results = mockedListOfResults
            )
        )
    }
}