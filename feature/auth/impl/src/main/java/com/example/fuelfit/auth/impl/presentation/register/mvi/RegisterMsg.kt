package com.example.fuelfit.auth.impl.presentation.register.mvi

internal sealed interface RegisterMsg {
    data class SetUsername(val value: String) : RegisterMsg
    data class SetEmail(val value: String) : RegisterMsg
    data class SetPassword(val value: String) : RegisterMsg
    data object Loading : RegisterMsg
    data object Success : RegisterMsg
    data class Error(val message: String) : RegisterMsg
}
