package com.example.stove.data.dto.configuration

import com.example.stove.data.dto.AddonDto
import com.example.stove.data.dto.ComponentDto
import com.example.stove.data.dto.ConfigComponentDto
import com.example.stove.data.dto.TypeDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewConfigurationResponse(
    val id: Int,
    val name: String,
    @Json(name = "is_template") val isTemplate: Boolean,
    @Json(name = "is_locked") val isLocked: Boolean,
    @Json(name = "created_at") val createdAt: String,
    @Json(name = "total_price") val totalPrice: Int,
    @Json(name = "stove_type") val stoveType: TypeDto,
    val components: List<ConfigComponentDto>,
    val addons: List<AddonDto>
)
