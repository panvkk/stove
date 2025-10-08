package com.example.stove.domain.usecase.auth

import com.example.stove.domain.infrastructure.NetworkCacheManager
import com.example.stove.domain.repository.AuthRepository
import javax.inject.Inject

class CheckAuthUseCase @Inject  constructor(
    private val authRepository: AuthRepository,
    private val networkCacheManager: NetworkCacheManager
){
    suspend fun invoke() {
        if(!authRepository.checkAuthentication()) {
            networkCacheManager.clearCache()
        }
    }
}