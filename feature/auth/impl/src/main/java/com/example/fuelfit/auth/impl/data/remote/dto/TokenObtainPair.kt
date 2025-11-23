package com.example.fuelfit.auth.impl.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class TokenObtainPair(
    val access: String,
    val refresh: String
)
