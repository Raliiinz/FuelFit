package com.example.fuelfit.ui.splash.mvi


sealed interface SplashLabel {
    data object NavigateToMain : SplashLabel
    data object NavigateToLogin : SplashLabel

    data class ShowError(val message: String) : SplashLabel
}
