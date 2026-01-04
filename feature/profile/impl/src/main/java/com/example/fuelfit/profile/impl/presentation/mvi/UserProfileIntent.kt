package com.example.fuelfit.profile.impl.presentation.mvi

internal sealed interface UserProfileIntent {
    object LoadProfile : UserProfileIntent
    object Refresh : UserProfileIntent

    data class WeightChanged(val value: String) : UserProfileIntent
    data class HeightChanged(val value: String) : UserProfileIntent
    data class AgeChanged(val value: String) : UserProfileIntent

    object SaveClicked : UserProfileIntent
    object LogoutClicked : UserProfileIntent
}
