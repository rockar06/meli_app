package com.example.meliapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * [Address] only includes necessary attributes to display basic data.
 */

@Parcelize
data class Address(
    @SerializedName("state_name") val stateName: String,
    @SerializedName("city_name") val cityName: String
) : Parcelable