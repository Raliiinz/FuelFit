package com.example.fuelfit.auth.impl.presentation.login.mvi

internal sealed interface LoginLabel {
    data object NavigateToWorkoutSession : LoginLabel
    data object NavigateToRegister : LoginLabel
    data class ShowError(val message: String) : LoginLabel
}
