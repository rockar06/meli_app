package com.example.meliapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * [Values] only includes necessary attributes to display basic data.
 */

@Parcelize
data class Values(

    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("results") val results: Int
) : Parcelable