package com.example.stove.domain.usecase.auth

import com.example.stove.core.AuthState
import com.example.stove.domain.repository.AuthRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ObserveAuthStateUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    fun invoke() : StateFlow<AuthState> = authRepository.authState
}