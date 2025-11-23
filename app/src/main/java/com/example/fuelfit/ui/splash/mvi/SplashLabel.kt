package com.example.fuelfit.ui.splash.mvi

sealed interface SplashLabel {
    data object NavigateToLogin : SplashLabel
    data object NavigateToWorkoutSession : SplashLabel
}