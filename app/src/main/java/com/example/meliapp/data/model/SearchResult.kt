package com.example.meliapp.data.model

import com.google.gson.annotations.SerializedName

/**
 * [SearchResult] only includes necessary properties to display data, filters and sorts are
 * out of the scope.
 */
data class SearchResult(
    @SerializedName("paging") val paging: Paging,
    @SerializedName("results") val results: List<Results>,
)