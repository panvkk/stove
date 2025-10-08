package com.example.stove.presentation.viewmodel

import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stove.core.AuthState
import com.example.stove.data.remote.interceptor.AuthAuthenticator
import com.example.stove.domain.usecase.auth.CheckAuthUseCase
import com.example.stove.domain.usecase.auth.LogoutUseCase
import com.example.stove.domain.usecase.auth.ObserveAuthStateUseCase
import com.example.stove.presentation.callback.SnackbarEventBus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface SnackbarEventSource {
    fun postSnackbar(message: String)
}

sealed interface SnackbarEvent {
    data class ShowSnackbar(val message: String): SnackbarEvent
}
@HiltViewModel
class GlobalViewModel @Inject constructor(
    private val authAuthenticator: AuthAuthenticator,

    private val logoutUseCase: LogoutUseCase,

    private val observeAuthStateUseCase: ObserveAuthStateUseCase,

    private val checkAuthUseCase: CheckAuthUseCase,

    val snackbarEventBus: SnackbarEventBus
) : ViewModel(){
    val authState: StateFlow<AuthState> = observeAuthStateUseCase.invoke()
    val snackbarHostState = SnackbarHostState()

    init {
        viewModelScope.launch {
            checkAuthUseCase.invoke()
        }
        viewModelScope.launch {
            authAuthenticator.authEventFlow.collect {
                Log.d("GlobalViewModel", "Received 401, calling logout!")
                logoutUseCase.invoke()
            }
        }
    }
}