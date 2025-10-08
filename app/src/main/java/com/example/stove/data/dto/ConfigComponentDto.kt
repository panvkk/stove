package com.example.stove.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ConfigComponentDto(
    @Json(name = "component_name") val componentName: String,
    @Json(name = "chosen_option") val chosenOption: OptionDto
)
