package com.example.fuelfit.profile.impl.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserProfileRequest(
    @SerialName( "weight_rounding")
    val weightRounding: String? = null,
    val height: Int? = null,
    val age: Int? = null
)
