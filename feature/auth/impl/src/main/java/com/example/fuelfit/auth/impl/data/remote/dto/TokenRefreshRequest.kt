package com.example.fuelfit.auth.impl.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class TokenRefreshRequest(
    val refresh: String
)
