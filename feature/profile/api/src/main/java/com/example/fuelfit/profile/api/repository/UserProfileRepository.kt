package com.example.fuelfit.profile.api.repository

import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.profile.api.model.UserProfileInfo

interface UserProfileRepository {

    suspend fun getCurrentUserProfile(): ResultWrapper<UserProfileInfo>

    suspend fun updateCurrentUserProfile(
        weight: String?,
        height: Int?,
        age: Int?
    ): ResultWrapper<UserProfileInfo>
}
