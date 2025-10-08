package com.example.stove.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stove.core.Resource
import com.example.stove.domain.model.auth.Login
import com.example.stove.domain.model.auth.Register
import com.example.stove.domain.usecase.auth.LoginUseCase
import com.example.stove.domain.usecase.auth.RegisterUseCase
import com.example.stove.presentation.model.LoginUiModel
import com.example.stove.presentation.model.RegisterUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AuthNavigatinoEvent {
    data object ToRegister: AuthNavigatinoEvent()
    data object ToLogin: AuthNavigatinoEvent()
}

data class InputInfo(
    val email: String = "",
    val password: String = "",
    val phoneNumber: String = "",
    val fullName: String = ""
)


sealed interface LoginUiState {
    data object Waiting : LoginUiState
    data object Success : LoginUiState
    data class Failure(val message: String) : LoginUiState
}

sealed interface RegisterUiState {
    data object Waiting : RegisterUiState
    data object Success : RegisterUiState
    data class Failure(val message: String) : RegisterUiState
}

sealed interface AuthUiState {
    data class Login(val state: LoginUiState) : AuthUiState
    data class Register(val state: RegisterUiState) : AuthUiState
}

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {
    private val _navigationEvents = Channel<AuthNavigatinoEvent>()
    val navigationEvent = _navigationEvents.receiveAsFlow()

    private val _authUiState: MutableStateFlow<AuthUiState> = MutableStateFlow(AuthUiState.Login(LoginUiState.Waiting))
    val authUiState: StateFlow<AuthUiState> = _authUiState

    private val _inputInfo = MutableStateFlow(InputInfo())
    val inputInfo: StateFlow<InputInfo> = _inputInfo.asStateFlow()

    @Inject
    lateinit var loginCase: LoginUseCase

    @Inject
    lateinit var registerCase: RegisterUseCase

    fun login() {
        viewModelScope.launch {
            if(_inputInfo.value.email != "" && _inputInfo.value.password != "") {

                val request = LoginUiModel(
                    email = _inputInfo.value.email,
                    password = _inputInfo.value.password
                )

                val result =  loginCase.invoke(request.toDomain())
                when(result) {
                    is Resource.SUCCESS<*> -> {
                        _authUiState.value = AuthUiState.Login(LoginUiState.Success)
                    }
                    is Resource.FAILURE -> {
                        _authUiState.value = AuthUiState.Login(LoginUiState.Failure(result.error.message ?: "Unknown error"))
                    }
                    else -> _authUiState.value = AuthUiState.Login(LoginUiState.Waiting)
                }
            }
        }
    }

    fun register() {
        viewModelScope.launch {
            if(_inputInfo.value.email != "" &&
                _inputInfo.value.password != "" &&
                _inputInfo.value.fullName != "" &&
                _inputInfo.value.phoneNumber != "") {

                val request = RegisterUiModel(
                    fullName = _inputInfo.value.fullName,
                    phoneNumber = _inputInfo.value.phoneNumber,
                    email = _inputInfo.value.email,
                    password = _inputInfo.value.password
                )

                val result = registerCase.invoke(request.toDomain())
                when(result) {
                    is Resource.SUCCESS<*> -> {
                        _authUiState.value = AuthUiState.Register(RegisterUiState.Success)
                    }
                    is Resource.FAILURE -> {
                        _authUiState.value = AuthUiState.Register(RegisterUiState.Failure(result.error.message ?: "Unknown error"))
                    }
                    else -> _authUiState.value = AuthUiState.Register(RegisterUiState.Waiting)
                }
            }
        }
    }


    fun toRegistration() {
        viewModelScope.launch {
            _authUiState.value = AuthUiState.Register(state = RegisterUiState.Waiting)
            _navigationEvents.send(AuthNavigatinoEvent.ToRegister)
        }
    }

    fun toLogin() {
        viewModelScope.launch {
            _authUiState.value = AuthUiState.Login(state = LoginUiState.Waiting)
            _navigationEvents.send(AuthNavigatinoEvent.ToLogin)
        }
    }

    fun onEmailChange(email: String) {
        _inputInfo.update{
            _inputInfo.value.copy(email = email)
        }
    }
    fun onPasswordChange(password: String) {
        _inputInfo.update {
            _inputInfo.value.copy(password = password)
        }
    }
    fun onFullNameChange(fullName: String) {
        _inputInfo.update {
            _inputInfo.value.copy(fullName = fullName)
        }
    }
    fun onPhoneNumberChange(phoneNumber: String) {
        _inputInfo.update {
            _inputInfo.value.copy(phoneNumber = phoneNumber)
        }
    }

    private fun RegisterUiModel.toDomain() = Register(fullName, phoneNumber, email, password)
    private fun LoginUiModel.toDomain() = Login(email, password)
}