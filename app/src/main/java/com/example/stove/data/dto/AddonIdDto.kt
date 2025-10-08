package com.example.stove.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddonIdDto(
    @Json(name = "addon_id")
    val addonId: Int
)
