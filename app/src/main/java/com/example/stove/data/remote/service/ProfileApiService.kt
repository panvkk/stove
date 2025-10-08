package com.example.stove.data.remote.service

import com.example.stove.data.dto.UserInfoDto
import retrofit2.Response
import retrofit2.http.GET

interface ProfileApiService {
    @GET("api/v1/users/me")
    suspend fun getUserInfo() : Response<UserInfoDto>
}