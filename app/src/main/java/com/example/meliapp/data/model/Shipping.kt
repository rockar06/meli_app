package com.example.meliapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * [Shipping] only includes necessary attributes to display basic data.
 */

@Parcelize
data class Shipping(
    @SerializedName("free_shipping") val freeShipping: Boolean,
    @SerializedName("mode") val mode: String,
    @SerializedName("tags") val tags: List<String>,
    @SerializedName("logistic_type") val logisticType: String,
    @SerializedName("store_pick_up") val storePickUp: Boolean
) : Parcelable