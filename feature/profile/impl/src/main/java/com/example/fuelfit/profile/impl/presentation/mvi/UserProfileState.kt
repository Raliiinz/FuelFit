package com.example.fuelfit.profile.impl.presentation.mvi

import com.example.fuelfit.profile.api.model.UserProfileInfo

internal data class UserProfileState(
    val profile: UserProfileInfo? = null,
    val weightInput: String = "",
    val heightInput: String = "",
    val ageInput: String = "",
    val heightError: Boolean = false,
    val ageError: Boolean = false,
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val error: String? = null
)
