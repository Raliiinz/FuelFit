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
    UserFriendlyError.Network -> ErrorMessages.NETWORK
    UserFriendlyError.Unauthorized -> ErrorMessages.UNAUTHORIZED
    UserFriendlyError.Forbidden -> ErrorMessages.FORBIDDEN
    UserFriendlyError.NotFound -> ErrorMessages.NOT_FOUND
    UserFriendlyError.ClientError -> ErrorMessages.CLIENT_ERROR
    UserFriendlyError.Server -> ErrorMessages.SERVER_ERROR
    UserFriendlyError.Unknown -> ErrorMessages.UNKNOWN
}
