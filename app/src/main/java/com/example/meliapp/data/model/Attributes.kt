package com.example.meliapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * [Attributes] only includes necessary attributes to display basic data.
 */

@Parcelize
data class Attributes(
    @SerializedName("attribute_group_id") val attributeGroupId: String,
    @SerializedName("name") val name: String,
    @SerializedName("values") val values: List<Values>,
    @SerializedName("value_name") val valueName: String,
    @SerializedName("attribute_group_name") val attributeGroupName: String,
    @SerializedName("id") val id: String,
    @SerializedName("value_id") val valueId: Int
): Parcelable