package com.example.fuelfit.exercise.impl.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExerciseCreateRequest(
    val category: Int,
    val muscles: List<Int>,
    @SerialName("muscles_secondary")
    val musclesSecondary: List<Int>,
    val equipment: List<Int>,
    val variations: Int?,
    @SerialName("license_author")
    val licenseAuthor: String?
)
