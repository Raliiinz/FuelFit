package com.example.fuelfit.auth.impl.presentation.login.mvi

internal sealed interface LoginIntent {
    data class UsernameChanged(val value: String) : LoginIntent
    data class PasswordChanged(val value: String) : LoginIntent
    data class EmailChanged(val value: String) : LoginIntent
    data object Submit : LoginIntent
    data object NavigateToRegister : LoginIntent
}