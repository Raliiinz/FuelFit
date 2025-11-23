package com.example.fuelfit.ui.splash.mvi

sealed interface SplashMsg {
    data object Loading : SplashMsg
    data object Authenticated : SplashMsg
    data object Unauthenticated : SplashMsg
}