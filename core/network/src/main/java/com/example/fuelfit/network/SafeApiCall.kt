package com.example.fuelfit.network

import com.example.fuelfit.model.ApiError
import com.example.fuelfit.model.ResultWrapper
import kotlinx.coroutines.TimeoutCancellationException
import retrofit2.HttpException
import java.io.IOException

private const val NO_CONNECTION_ERROR_CODE = -1
private const val UNKNOWN_ERROR_CODE = -2

private val CLIENT_ERROR_RANGE = 400..499
private val SERVER_ERROR_RANGE = 500..599

suspend fun <T> safeApiCall(apiCall: suspend () -> T): ResultWrapper<T> {
    return try {
        val result = apiCall()
        ResultWrapper.Success(result)
    } catch (e: HttpException) {
        val code = e.code()
        val message = when (code) {
            in CLIENT_ERROR_RANGE -> "Ошибка запроса: ${e.message()}"
            in SERVER_ERROR_RANGE -> "Ошибка сервера: ${e.message()}"
            else -> "Неизвестная ошибка: ${e.message()}"
        }
        ResultWrapper.Error(ApiError(code, message))
    } catch (e: IOException) {
        ResultWrapper.Error(ApiError(NO_CONNECTION_ERROR_CODE, "Нет подключения к интернету: ${e.message}"))
    } catch (e: TimeoutCancellationException) {
        ResultWrapper.Error(ApiError(UNKNOWN_ERROR_CODE, e.localizedMessage ?: "Неизвестная ошибка"))
    }
}
