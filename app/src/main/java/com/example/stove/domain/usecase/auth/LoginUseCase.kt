package com.example.stove.domain.usecase.auth

import com.example.stove.core.Resource
import com.example.stove.data.dto.LoginRequest
import com.example.stove.domain.model.auth.Login
import com.example.stove.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend fun invoke(request: Login) : Resource<Unit> {
        val response = repository.login(request.toData())

        return when(response) {
            is Resource.SUCCESS -> Resource.SUCCESS(Unit)
            is Resource.FAILURE -> Resource.FAILURE(response.error)
            else -> Resource.LOADING()
        }
    }

    private fun Login.toData() = LoginRequest(email, password)
}