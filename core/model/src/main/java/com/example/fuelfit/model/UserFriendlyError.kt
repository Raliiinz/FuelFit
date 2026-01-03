package com.example.fuelfit.model

sealed class UserFriendlyError {
    object Network : UserFriendlyError()
    object Unauthorized : UserFriendlyError()
    object Forbidden : UserFriendlyError()
    object NotFound : UserFriendlyError()
    object ClientError : UserFriendlyError()
    object Server : UserFriendlyError()
    object Unknown : UserFriendlyError()
}

fun mapApiErrorToUserFriendly(error: ApiError): UserFriendlyError = when (error.code) {
    401 -> UserFriendlyError.Unauthorized
    403 -> UserFriendlyError.Forbidden
    404 -> UserFriendlyError.NotFound
    in 400..499 -> UserFriendlyError.ClientError
    in 500..599 -> UserFriendlyError.Server
    else -> UserFriendlyError.Unknown
}

fun getErrorMessage(error: UserFriendlyError): String = when (error) {
    UserFriendlyError.Network -> "Ошибка сети. Проверьте соединение"
    UserFriendlyError.Unauthorized -> "Необходимо войти в аккаунт"
    UserFriendlyError.Forbidden -> "Доступ к данным запрещён"
    UserFriendlyError.NotFound -> "Данные не найдены"
    UserFriendlyError.ClientError -> "Ошибка запроса. Попробуйте снова"
    UserFriendlyError.Server -> "Сервер временно недоступен"
    UserFriendlyError.Unknown -> "Произошла неизвестная ошибка"
}
