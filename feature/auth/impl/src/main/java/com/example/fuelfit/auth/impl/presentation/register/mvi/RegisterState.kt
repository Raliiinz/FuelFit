package com.example.fuelfit.auth.impl.presentation.register.mvi

internal data class RegisterState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)
