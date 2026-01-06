package com.example.fuelfit.ui.splash.mvi

sealed interface SplashAction {
    data object Load : SplashAction
}
