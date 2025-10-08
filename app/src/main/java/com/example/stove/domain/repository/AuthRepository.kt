package com.example.stove.domain.repository

import com.example.stove.core.AuthState
import com.example.stove.core.Resource
import com.example.stove.data.dto.LoginRequest
import com.example.stove.data.dto.RegisterRequest
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
    val authState: StateFlow<AuthState>

    suspend fun register(request: RegisterRequest) : Resource<Unit>

    suspend fun login(request: LoginRequest) : Resource<Unit>

    suspend fun checkAuthentication() : Boolean

    suspend fun logout() : Unit
}