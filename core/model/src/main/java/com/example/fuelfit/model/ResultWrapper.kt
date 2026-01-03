package com.example.fuelfit.model
sealed class ResultWrapper<out T> {
    data class Success<out T>(val data: T) : ResultWrapper<T>()
    data class Error(val error: ApiError) : ResultWrapper<Nothing>()
}

data class ApiError(
    val code: Int,
    val message: String
)
