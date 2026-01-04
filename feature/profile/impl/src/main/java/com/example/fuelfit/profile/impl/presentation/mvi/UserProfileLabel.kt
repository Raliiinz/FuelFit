package com.example.fuelfit.profile.impl.presentation.mvi

internal sealed interface UserProfileLabel {
    data class ShowError(val message: String) : UserProfileLabel
    object ShowProfileSaved : UserProfileLabel
    object LoggedOut : UserProfileLabel
}
