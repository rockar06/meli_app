package com.example.meliapp.data.model

import com.google.gson.annotations.SerializedName

/**
 * [Paging] only includes necessary attributes to display basic data.
 */

data class Paging(
    @SerializedName("total") val total: Int,
    @SerializedName("primary_results") val primaryResults: Int,
    @SerializedName("offset") val offset: Int,
    @SerializedName("limit") val limit: Int
)