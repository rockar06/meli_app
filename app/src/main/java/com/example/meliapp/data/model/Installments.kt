package com.example.meliapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Installments(
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("amount") val amount: Double,
    @SerializedName("rate") val rate: Double
) : Parcelable