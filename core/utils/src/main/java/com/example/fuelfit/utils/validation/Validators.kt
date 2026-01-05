package com.example.fuelfit.utils.validation

object Validators {

    private val EMAIL_REGEX =
        Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

    private val PASSWORD_REGEX =
        Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")

    fun isValidEmail(email: String): Boolean =
        EMAIL_REGEX.matches(email)

    fun isValidPassword(password: String): Boolean =
        PASSWORD_REGEX.matches(password)
}
