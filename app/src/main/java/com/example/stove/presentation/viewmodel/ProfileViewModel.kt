package com.example.stove.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stove.core.Resource
import com.example.stove.data.favourite.Favourite
import com.example.stove.domain.usecase.auth.LogoutUseCase
import com.example.stove.domain.usecase.profile.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ProfileUiState {
    data class User(val state: UserUiState): ProfileUiState
    data class Favourites(
        val favouritesList: List<Favourite>
    ) : ProfileUiState
}

sealed interface UserUiState {
    data class Success(
        val fullName: String,
        val phoneNumber: String
    ) : UserUiState
    data class Failure(
        val errorMessage: String
    ) : UserUiState
    data object Loading : UserUiState
}
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserInfoCase: GetUserInfoUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {
    private val _profileUiState: MutableStateFlow<ProfileUiState> =
        MutableStateFlow(ProfileUiState.User(UserUiState.Loading))
    val profileUiState: StateFlow<ProfileUiState> = _profileUiState

    init {
        loadUserInfo()
    }

    fun loadUserInfo() {
        viewModelScope.launch {
            val response = getUserInfoCase.invoke()

            when (response) {
                is Resource.SUCCESS -> {
                    _profileUiState.update {
                        ProfileUiState.User(
                            UserUiState.Success(
                                response.data.fullName,
                                response.data.phoneNumber
                            )
                        )
                    }
                }

                is Resource.FAILURE -> {
                    _profileUiState.update {
                        ProfileUiState.User(
                            UserUiState.Failure(response.error.message ?: "Неизвестная ошибка")
                        )
                    }
                }

                else -> _profileUiState.value = ProfileUiState.User(UserUiState.Loading)
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase.invoke()
        }
    }
}