package com.example.fuelfit.auth.impl.data.mapper

import com.example.fuelfit.auth.api.model.AuthTokens
import com.example.fuelfit.auth.impl.data.remote.dto.Login
import com.example.fuelfit.auth.impl.data.remote.dto.TokenRefresh

internal class AuthMapper {

    fun mapLoginToDomain(login: Login): AuthTokens {
        return AuthTokens(
            access = login.token,
            refresh = ""
        )
    }

    fun mapTokenRefreshToDomain(tokenRefresh: TokenRefresh, refresh: String): AuthTokens {
        return AuthTokens(
            access = tokenRefresh.access,
            refresh = refresh
        )
    }
}
