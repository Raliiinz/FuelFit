package com.example.fuelfit.profile.impl.presentation.mvi

import com.example.fuelfit.profile.api.model.UserProfileInfo

internal sealed interface UserProfileMsg {
    object Loading : UserProfileMsg
    data class Error(val message: String) : UserProfileMsg
    data class ProfileLoaded(val profile: UserProfileInfo) : UserProfileMsg

    data class WeightChanged(val value: String) : UserProfileMsg
    data class HeightChanged(val value: String) : UserProfileMsg
    data class AgeChanged(val value: String) : UserProfileMsg

    data class HeightChangedError(val isError: Boolean, val message: String? = null) : UserProfileMsg
    data class AgeChangedError(val isError: Boolean, val message: String? = null) : UserProfileMsg
    object Saving : UserProfileMsg
    object Saved : UserProfileMsg
}
