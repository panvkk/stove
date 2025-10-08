package com.example.stove.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OptionIdDto(
    @Json(name = "option_id")
    val optionId: Int
)
