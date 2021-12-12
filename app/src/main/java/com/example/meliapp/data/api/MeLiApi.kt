package com.example.meliapp.data.api

import com.example.meliapp.data.datasource.MELI_STARTING_PAGE_INDEX
import com.example.meliapp.data.datasource.NETWORK_PAGE_SIZE
import com.example.meliapp.data.model.SearchResult
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * [MeLiApi] is the interface where we can declare each endpoint to use from backend
 */

interface MeLiApi {

    /**
     * Send the [productToSearch] as query parameter in the get request
     * @return the [SearchResult] object containing all data found
     */
    @GET("sites/MLM/search")
    suspend fun searchForProducts(
        @Query("q") productToSearch: String,
        @Query("offset") offset: Int = MELI_STARTING_PAGE_INDEX,
        @Query("limit") limit: Int = NETWORK_PAGE_SIZE
    ): SearchResult
}