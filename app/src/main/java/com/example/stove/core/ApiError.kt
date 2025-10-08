package com.example.stove.core

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiError(
    val timestamp: String,
    val message: String,
    val path: String
)
