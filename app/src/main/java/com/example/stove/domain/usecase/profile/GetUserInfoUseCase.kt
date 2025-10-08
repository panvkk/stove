package com.example.stove.domain.usecase.profile

import com.example.stove.core.Resource
import com.example.stove.domain.model.profile.UserInfo
import com.example.stove.domain.repository.ProfileRepository
import com.example.stove.presentation.model.UserInfoUiModel
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend fun invoke() : Resource<UserInfoUiModel> {
        return when(val response = profileRepository.getUserInfo()) {
            is Resource.SUCCESS -> {
                Resource.SUCCESS(response.data.toUiModel())
            }
            is Resource.FAILURE -> {
                Resource.FAILURE(response.error)
            }
            else -> Resource.LOADING()
        }
    }

    private fun UserInfo.toUiModel() = UserInfoUiModel(fullName ?: "", phoneNumber ?: "", email ?: "")
}