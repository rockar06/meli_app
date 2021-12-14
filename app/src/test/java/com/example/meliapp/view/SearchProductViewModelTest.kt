package com.example.meliapp.view

import androidx.paging.PagingData
import com.example.meliapp.MainCoroutineRule
import com.example.meliapp.data.model.Results
import com.example.meliapp.data.repository.ProductsRepository
import com.example.meliapp.view.viewmodel.SearchProductViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.*

@ExperimentalCoroutinesApi
class SearchProductViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val mockRepository: ProductsRepository = mock()
    private val systemUnderTest = SearchProductViewModel(mockRepository)
    private val mockQuery = "query"

    @Test
    fun searchProduct_onSuccessfullyReturnResults_returnNewSearch() =
        mainCoroutineRule.runBlockingTest {
            whenever(mockRepository.getProductsFromSearch(any())).thenAnswer {
                emptyFlow<PagingData<Results>>()
            }
            systemUnderTest.searchProduct(mockQuery)
            verify(mockRepository, atMost(1)).getProductsFromSearch(any())
        }

    @Test
    fun searchProduct_onCallSameQuery_returnPreviousResult() =
        mainCoroutineRule.runBlockingTest {
            whenever(mockRepository.getProductsFromSearch(any())).thenAnswer {
                emptyFlow<PagingData<Results>>()
            }
            systemUnderTest.searchProduct(mockQuery)
            systemUnderTest.searchProduct(mockQuery)
            verify(mockRepository, atMost(1)).getProductsFromSearch(any())
        }
}
