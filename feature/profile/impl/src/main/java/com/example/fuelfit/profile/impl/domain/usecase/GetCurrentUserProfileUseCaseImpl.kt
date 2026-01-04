package com.example.fuelfit.profile.impl.domain.usecase

import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.profile.api.model.UserProfileInfo
import com.example.fuelfit.profile.api.repository.UserProfileRepository
import com.example.fuelfit.profile.api.usecase.GetCurrentUserProfileUseCase

internal class GetCurrentUserProfileUseCaseImpl(
    private val repository: UserProfileRepository
) : GetCurrentUserProfileUseCase {

    override suspend fun invoke(
    ): ResultWrapper<UserProfileInfo> {
        return repository.getCurrentUserProfile()
    }
}
