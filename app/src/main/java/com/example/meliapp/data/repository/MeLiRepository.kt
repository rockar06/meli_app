package com.example.meliapp.data.repository

import com.example.meliapp.data.model.SearchResult
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * [MeLiRepository] is the interface where we can declare each endpoint to use from backend
 */

interface MeLiRepository {

    /**
     * Send the [productToSearch] as query parameter in the get request
     * @return the [SearchResult] object containing all data found
     */
    @GET("sites/MLM/search")
    suspend fun searchForProducts(@Query("q") productToSearch: String): SearchResult
}