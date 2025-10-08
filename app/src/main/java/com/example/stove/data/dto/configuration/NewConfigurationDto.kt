package com.example.stove.data.dto.configuration

import com.example.stove.data.dto.AddonIdDto
import com.example.stove.data.dto.OptionIdDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewConfigurationDto(
    @Json(name = "stove_type_id")
    val stoveTypeId: Int,
    val name: String,
    val choices: List<OptionIdDto>,
    val addons: List<AddonIdDto>
)
