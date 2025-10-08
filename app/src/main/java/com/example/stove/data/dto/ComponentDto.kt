package com.example.stove.data.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName

data class ComponentDto(
    val id: Int,
    val name: String,
    val description: String,
    @SerializedName("is_required") val isRequired: Boolean?,
    @SerializedName("allow_multiple_choices") val allowMultipleChoices: Boolean?,
    @SerializedName("component_options") val componentOptions: Boolean?
)
