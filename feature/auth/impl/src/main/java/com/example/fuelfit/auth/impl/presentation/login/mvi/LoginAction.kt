package com.example.fuelfit.auth.impl.presentation.login.mvi

internal sealed interface LoginAction {
    data object Init : LoginAction
}
