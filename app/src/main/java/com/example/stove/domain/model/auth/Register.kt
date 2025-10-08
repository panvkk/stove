package com.example.stove.domain.model.auth

data class Register(
    val fullName: String,
    val phoneNumber: String,
    val email: String,
    val password: String
)