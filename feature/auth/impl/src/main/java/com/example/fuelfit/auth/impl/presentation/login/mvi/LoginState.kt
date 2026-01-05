package com.example.fuelfit.auth.impl.presentation.login.mvi

data class LoginState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val usernameError: Boolean = false,
    val emailError: Boolean = false,
    val passwordError: Boolean = false
)
