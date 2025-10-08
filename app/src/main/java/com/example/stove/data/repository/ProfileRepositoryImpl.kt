package com.example.stove.data.repository

import android.util.Log
import com.example.stove.core.Resource
import com.example.stove.data.dto.UserInfoDto
import com.example.stove.data.remote.service.ProfileApiService
import com.example.stove.domain.model.profile.UserInfo
import com.example.stove.domain.repository.ProfileRepository
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

const val PROFILE_TAG = "ProfileRepository"

class ProfileRepositoryImpl @Inject constructor(
    val apiService: ProfileApiService
) : ProfileRepository {
    override suspend fun getUserInfo(): Resource<UserInfo> {
        return try {
            val response = apiService.getUserInfo()

            if(response.isSuccessful) {
                Resource.SUCCESS(response.body()?.toDomain() ?: UserInfo(null, null, null))
            } else {
                Log.e(PROFILE_TAG, response.errorBody()?.string() ?: "Unknown error.")
                Resource.FAILURE(Throwable("Error fetching data from the server."))
            }
        } catch(e: HttpException) {
            Log.e(PROFILE_TAG, e.message ?: "Unknown error.")
            Resource.FAILURE(Throwable(e.message))
        } catch(e: IOException) {
            Log.e(PROFILE_TAG, e.message ?: "Unknown error.")
            Resource.FAILURE(Throwable("Network error. Check your connection."))
        }
    }

    private fun UserInfoDto.toDomain() = UserInfo(fullName, phoneNumber, email)
}