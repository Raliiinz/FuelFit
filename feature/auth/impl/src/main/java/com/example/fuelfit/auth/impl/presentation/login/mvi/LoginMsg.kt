package com.example.fuelfit.auth.impl.presentation.login.mvi

internal sealed interface LoginMsg {
    data class SetUsername(val value: String) : LoginMsg
    data class SetPassword(val value: String) : LoginMsg
    data class SetEmail(val value: String) : LoginMsg
    data object Loading : LoginMsg
    data object Success : LoginMsg
    data class Error(val message: String) : LoginMsg
}
