package me.naingaungluu.dynamiclocalization.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LocalizationData(
    val name: String,
    val value: String,
    @SerializedName("plural_value")
    val pluralValue: String,
    @SerializedName("ab_value")
    val abValue: String,
    val args: Int
) : Serializable