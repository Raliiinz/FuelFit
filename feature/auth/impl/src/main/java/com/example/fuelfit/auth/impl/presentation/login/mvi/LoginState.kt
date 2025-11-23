package com.example.fuelfit.auth.impl.presentation.login.mvi

internal data class LoginState(
    val username: String = "",
    val password: String = "",
    val email: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)
