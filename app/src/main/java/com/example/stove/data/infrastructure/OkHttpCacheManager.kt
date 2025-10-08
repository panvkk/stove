package com.example.stove.data.infrastructure

import com.example.stove.domain.infrastructure.NetworkCacheManager
import okhttp3.OkHttpClient
import javax.inject.Inject

class OkHttpCacheManager @Inject constructor(
    private val okHttpClient: OkHttpClient
) : NetworkCacheManager {
    override suspend fun clearCache() {
        okHttpClient.cache?.evictAll()
    }
}