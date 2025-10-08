package com.example.stove.data.repository

import android.util.Log
import coil.network.HttpException
import com.example.stove.core.AuthState
import com.example.stove.core.Resource
import com.example.stove.data.dto.LoginRequest
import com.example.stove.data.dto.RegisterRequest
import com.example.stove.data.local.TokenManager
import com.example.stove.data.remote.service.AuthenticationApiService
import com.example.stove.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okio.IOException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthenticationApiService,
    private val tokenManager: TokenManager
) : AuthRepository {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    override val authState: StateFlow<AuthState> = _authState

    override suspend fun login(request: LoginRequest): Resource<Unit> {
        try {
            val response = authService.login(request)

            if(response.isSuccessful) {
                if(!response.body()?.token.isNullOrEmpty()) {
                    tokenManager.putToken(response.body()?.token)
                    Log.i("AuthRepository", "Login success")
                    _authState.value = AuthState.Authenticated
                    return Resource.SUCCESS(Unit)
                } else {
                    return Resource.FAILURE(Throwable("Authentification error."))
                }
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error."
                // !!! Так делать не хорошо, надо потом сделать класс ApiError
                return Resource.FAILURE(Throwable(errorBody))
            }

        } catch(e: HttpException) {
            return Resource.FAILURE(Throwable(e.message))
        } catch(e: IOException) {
            return Resource.FAILURE(Throwable("Network error. Check your connection."))
        }
    }

    override suspend fun register(request: RegisterRequest): Resource<Unit> {
        try {
            val response = authService.register(request)

            if(response.isSuccessful) {
                if(!response.body()?.token.isNullOrEmpty()) {
                    tokenManager.putToken(response.body()?.token)
                    Log.i("AuthRepository", "Register success")
                    _authState.value = AuthState.Authenticated
                    return Resource.SUCCESS(Unit)
                } else {
                    Log.e("AuthRepository", "Auth error")
                    return Resource.FAILURE(Throwable("Authentification error."))
                }
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error."
                Log.e("AuthRepository", "Error from server: $errorBody")
                // !!! Так делать не хорошо, надо потом сделать класс ApiError
                return Resource.FAILURE(Throwable(errorBody))
            }

        } catch(e: HttpException) {
            Log.e("AuthRepository", e.message ?: "Http error")
            return Resource.FAILURE(Throwable(e.message))
        } catch(e: IOException) {
            Log.e("AuthRepository", e.message ?: "IO error.")
            return Resource.FAILURE(Throwable("Network error. Check your connection."))
        }
    }

    override suspend fun checkAuthentication(): Boolean {
        if(tokenManager.isLoggedIn()) {
            _authState.value = AuthState.Authenticated
            return true
        } else {
            _authState.value = AuthState.Unauthenticated
            return false
        }

    }

    override suspend fun logout() {
        tokenManager.clearToken()
        _authState.value = AuthState.Unauthenticated
    }
}