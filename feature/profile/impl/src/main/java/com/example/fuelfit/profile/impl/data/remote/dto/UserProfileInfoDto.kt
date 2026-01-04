package com.example.fuelfit.profile.impl.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfileInfoDto(
    val username: String,
    val email: String,
    @SerialName("is_trustworthy")
    val isTrustworthy: Boolean,
    @SerialName("date_joined")
    val dateJoined: String,
    val gym: Int?,
    @SerialName("is_temporary")
    val isTemporary: Boolean,
    @SerialName("last_workout_notification")
    val lastWorkoutNotification: String?,
    @SerialName( "weight_rounding")
    val weightRounding: String?,
    val height: Int?,
    val age: Int?
)
