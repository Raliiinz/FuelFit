package com.example.fuelfit.profile.impl.domain.usecase

import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.profile.api.model.UserProfileInfo
import com.example.fuelfit.profile.api.repository.UserProfileRepository
import com.example.fuelfit.profile.api.usecase.UpdateCurrentUserProfileUseCase

internal class UpdateCurrentUserProfileUseCaseImpl(
    private val repository: UserProfileRepository
) : UpdateCurrentUserProfileUseCase {

    override suspend fun invoke(
        weight: String?,
        height: Int?,
        age: Int?
    ): ResultWrapper<UserProfileInfo> {
        return repository.updateCurrentUserProfile(
            weight = weight,
            height = height,
            age = age
        )
    }
}
