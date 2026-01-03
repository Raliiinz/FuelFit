package com.example.fuelfit.network

import com.example.fuelfit.model.ApiError
import com.example.fuelfit.model.ResultWrapper
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): ResultWrapper<T> {
    return try {
        val result = apiCall()
        ResultWrapper.Success(result)
    } catch (e: HttpException) {
        // Обрабатываем ошибки сервера 4xx, 5xx
        val code = e.code()
        val message = when (code) {
            in 400..499 -> "Ошибка запроса: ${e.message()}"
            in 500..599 -> "Ошибка сервера: ${e.message()}"
            else -> "Неизвестная ошибка: ${e.message()}"
        }
        ResultWrapper.Error(ApiError(code, message))
    } catch (e: IOException) {
        // Ошибки сети
        ResultWrapper.Error(ApiError(-1, "Нет подключения к интернету"))
    } catch (e: Exception) {
        // Прочие ошибки
        ResultWrapper.Error(ApiError(-2, e.localizedMessage ?: "Неизвестная ошибка"))
    }
}
