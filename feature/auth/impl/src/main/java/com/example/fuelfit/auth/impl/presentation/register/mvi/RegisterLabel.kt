package com.example.fuelfit.auth.impl.presentation.register.mvi

internal sealed interface RegisterLabel {
    data object NavigateToMain: RegisterLabel
    data class ShowError(val message: String) : RegisterLabel
    data object NavigateToLogin : RegisterLabel
}
