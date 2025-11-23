package com.example.fuelfit.auth.impl.presentation.register.mvi

internal sealed interface RegisterIntent {
    data class UsernameChanged(val value: String) : RegisterIntent
    data class EmailChanged(val value: String) : RegisterIntent
    data class PasswordChanged(val value: String) : RegisterIntent
    data object Submit : RegisterIntent
    data object NavigateToLogin : RegisterIntent
}
