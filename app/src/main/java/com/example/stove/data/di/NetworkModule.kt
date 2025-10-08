package com.example.stove.data.di

import android.content.Context
import com.example.stove.data.infrastructure.OkHttpCacheManager
import com.example.stove.data.remote.interceptor.AuthAuthenticator
import com.example.stove.data.remote.interceptor.AuthInterceptor
import com.example.stove.data.remote.service.AuthDesignerApiService
import com.example.stove.data.remote.service.AuthenticationApiService
import com.example.stove.data.remote.service.PublicDesignerApiService
import com.example.stove.data.remote.service.ProfileApiService
import com.example.stove.domain.infrastructure.NetworkCacheManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import javax.inject.Qualifier
import javax.inject.Singleton

private const val baseUrl = "https://local-abs-broadcast-ide.trycloudflare.com/"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun getMoshi() : Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun getLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    @PublicOkHttpCache
    fun getPublicHttpCache(@ApplicationContext context: Context) : Cache {
        val cacheDirectory = File(context.cacheDir, "public-http-cache")
        val cacheSize = 10 * 1024L * 1024L
        return Cache(cacheDirectory, cacheSize)
    }

    @Singleton
    @Provides
    @AuthOkHttpCache
    fun getAuthHttpCache(@ApplicationContext context: Context) : Cache {
        val cacheDirectory = File(context.cacheDir, "auth-http-cache")
        val cacheSize = 10 * 1024L * 1024L
        return Cache(cacheDirectory, cacheSize)
    }

    @Singleton
    @Provides
    @PublicOkHttpClient
    fun getPublicOkHttpClient(
        @PublicOkHttpCache
        cache: Cache,
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .cache(cache)
            .build()
    }

    @Singleton
    @Provides
    @AuthOkHttpClient
    fun getAuthOkHttpClient(
        @AuthOkHttpCache
        cache: Cache,
        loggingInterceptor: HttpLoggingInterceptor,
        authAuthenticator: AuthAuthenticator,
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .authenticator(authAuthenticator)
            .cache(cache)
            .build()
    }

    @Singleton
    @Provides
    fun getOkHttpCacheManager(
        @AuthOkHttpClient
        okHttpClient: OkHttpClient
    ) : NetworkCacheManager = OkHttpCacheManager(okHttpClient)

    @Provides
    @Singleton
    @PublicRetrofit
    fun getPublicRetrofit(moshi: Moshi, @PublicOkHttpClient okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    @AuthRetrofit
    fun getAuthRetrofit(moshi: Moshi, @AuthOkHttpClient okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }


    @Provides
    @Singleton
    fun getPublicDesignerService(@PublicRetrofit retrofit: Retrofit) : PublicDesignerApiService {
        return retrofit.create(PublicDesignerApiService::class.java)
    }

    @Provides
    @Singleton
    fun getAuthDesignerService(@AuthRetrofit retrofit: Retrofit) : AuthDesignerApiService {
        return retrofit.create(AuthDesignerApiService::class.java)
    }

    @Provides
    @Singleton
    fun getAuthService(@PublicRetrofit retrofit: Retrofit) : AuthenticationApiService {
        return retrofit.create(AuthenticationApiService::class.java)
    }

    @Provides
    @Singleton
    fun getProfileService(@AuthRetrofit retrofit: Retrofit) : ProfileApiService {
        return retrofit.create(ProfileApiService::class.java)
    }


    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class PublicOkHttpCache

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class AuthOkHttpCache

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class AuthOkHttpClient

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class PublicOkHttpClient

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class PublicRetrofit

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class AuthRetrofit
}