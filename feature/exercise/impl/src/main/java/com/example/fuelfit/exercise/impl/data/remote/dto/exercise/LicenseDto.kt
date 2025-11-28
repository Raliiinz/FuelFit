package com.example.fuelfit.exercise.impl.data.remote.dto.exercise

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LicenseDto(
    val id: Int,
    @SerialName("full_name")
    val fullName: String,
    @SerialName("short_name")
    val shortName: String,
    val url: String?
)

