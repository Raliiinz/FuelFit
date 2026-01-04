package com.example.fuelfit.profile.api.usecase

import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.profile.api.model.UserProfileInfo

interface UpdateCurrentUserProfileUseCase {
    suspend operator fun invoke(
        weight: String?,
        height: Int?,
        age: Int?
    ): ResultWrapper<UserProfileInfo>
}
