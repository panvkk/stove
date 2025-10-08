package com.example.stove.data.dto

import com.google.gson.annotations.JsonAdapter
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddonDto(
    val id: Int,
    val name: String,
    val description: String,
    val price: Int?
)
