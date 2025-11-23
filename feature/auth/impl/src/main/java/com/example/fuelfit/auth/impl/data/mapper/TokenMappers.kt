package com.example.fuelfit.auth.impl.data.mapper

import com.example.fuelfit.auth.api.model.AuthTokens
import com.example.fuelfit.auth.impl.data.remote.dto.Login
import com.example.fuelfit.auth.impl.data.remote.dto.TokenObtainPair
import com.example.fuelfit.auth.impl.data.remote.dto.TokenRefresh

fun TokenObtainPair.toDomain(): AuthTokens =
    AuthTokens(
        access = access,
        refresh = refresh
    )

fun TokenRefresh.toDomain(refresh: String): AuthTokens =
    AuthTokens(
        access = this.access,
        refresh = refresh
    )

fun Login.toDomain(): AuthTokens =
    AuthTokens(
        access = token,
        refresh = ""
    )