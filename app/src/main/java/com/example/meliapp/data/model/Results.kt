package com.example.meliapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * [Results] only includes necessary attributes to display basic data.
 */

@Parcelize
data class Results(
    @SerializedName("id") val id: String,
    @SerializedName("site_id") val site_id: String,
    @SerializedName("title") val title: String,
    @SerializedName("price") val price: Int,
    @SerializedName("currency_id") val currencyId: String,
    @SerializedName("available_quantity") val availableQuantity: Int,
    @SerializedName("sold_quantity") val sold_quantity: Int,
    @SerializedName("condition") val condition: String,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("accepts_mercadopago") val acceptsMercadoPago: Boolean,
    @SerializedName("address") val address: Address,
    @SerializedName("shipping") val shipping: Shipping,
    @SerializedName("attributes") val attributes: List<Attributes>
) : Parcelable