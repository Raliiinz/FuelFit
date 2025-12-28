package com.example.fuelfit.auth.impl.presentation.login.mvi

internal sealed interface LoginLabel {
    data object NavigateToMain : LoginLabel
    data object NavigateToRegister : LoginLabel
    data class ShowError(val message: String) : LoginLabel
}
