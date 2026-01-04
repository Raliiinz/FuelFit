package com.example.fuelfit.profile.api.model

import java.time.LocalDate
import java.time.OffsetDateTime

data class UserProfileInfo(
    val username: String,
    val email: String,
    val isTrustworthy: Boolean,
    val dateJoined: OffsetDateTime?,
    val gym: Int?,
    val isTemporary: Boolean,
    val lastWorkoutNotification: LocalDate?,
    val weightRounding: String?,
    val height: Int?,
    val age: Int?
)
