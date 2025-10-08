package com.example.stove.domain.usecase.auth

import com.example.stove.core.Resource
import com.example.stove.data.dto.RegisterRequest
import com.example.stove.domain.model.auth.Register
import com.example.stove.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend fun invoke(request: Register) : Resource<Unit> {
        val response = repository.register(request.toData())

        return when(response) {
            is Resource.SUCCESS -> Resource.SUCCESS(Unit)
            is Resource.FAILURE -> Resource.FAILURE(response.error)
            else -> Resource.LOADING()
        }
    }

    private fun Register.toData() = RegisterRequest(fullName, phoneNumber, email, password)
}