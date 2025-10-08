package com.example.stove.data.dto

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.serialization.SerialName

@JsonClass(generateAdapter = true)
data class TypeDto (
    val id: Int,
    val name: String,
    val description: String,
    @Json(name = "base_price") val basePrice: Int?,
    @Json(name = "image_url") val imageUrl: String?
)