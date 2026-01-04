package com.example.fuelfit.profile.impl.data.remote

import com.example.fuelfit.profile.impl.data.remote.dto.UpdateUserProfileRequest
import com.example.fuelfit.profile.impl.data.remote.dto.UserProfileInfoDto
import retrofit2.http.*

internal interface UserProfileApiService {
    @GET("api/v2/userprofile/")
    suspend fun getCurrentUserProfile(): UserProfileInfoDto

    @POST("api/v2/userprofile/")
    suspend fun updateCurrentUserProfile(
        @Body body: UpdateUserProfileRequest
    ): UserProfileInfoDto
}
