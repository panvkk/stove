package com.example.stove.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserInfoDto(
    @Json(name = "full_name")
    val fullName: String?,
    @Json(name = "phone_number")
    val phoneNumber: String?,
    val email: String?
)
