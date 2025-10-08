package com.example.stove.data.remote.service

import com.example.stove.data.dto.AuthResponse
import com.example.stove.data.dto.LoginRequest
import com.example.stove.data.dto.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationApiService {
    @POST("api/v1/auth/register")
    suspend fun register(@Body request: RegisterRequest) : Response<AuthResponse>

    @POST("api/v1/auth/login")
    suspend fun login(@Body request: LoginRequest) : Response<AuthResponse>
}