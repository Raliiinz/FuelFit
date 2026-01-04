package com.example.fuelfit.profile.api.usecase

import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.profile.api.model.UserProfileInfo

interface GetCurrentUserProfileUseCase {
    suspend operator fun invoke(): ResultWrapper<UserProfileInfo>
}
