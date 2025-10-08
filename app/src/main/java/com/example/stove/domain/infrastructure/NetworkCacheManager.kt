package com.example.stove.domain.infrastructure

interface NetworkCacheManager {
    suspend fun clearCache()
}