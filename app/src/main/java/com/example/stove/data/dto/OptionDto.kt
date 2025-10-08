package com.example.stove.data.dto

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.serialization.SerialName

@JsonClass(generateAdapter = true)
data class OptionDto(
    val id: Int,
    val name: String,
    @Json(name = "price_modifier") val priceModifier: Int?,
    @Json(name = "image_url") val imageUrl: String?,
    @Json(name = "is_default") val isDefault: Boolean?
)
