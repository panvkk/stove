package com.example.stove.domain.usecase.auth

import com.example.stove.domain.infrastructure.NetworkCacheManager
import com.example.stove.domain.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    val authRepository: AuthRepository,
    val networkCacheManager: NetworkCacheManager
) {
    suspend fun invoke() {
        authRepository.logout()
        networkCacheManager.clearCache()
    }
}