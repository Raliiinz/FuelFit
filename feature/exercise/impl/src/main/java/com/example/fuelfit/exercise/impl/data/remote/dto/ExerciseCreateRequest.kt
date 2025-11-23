package com.example.fuelfit.exercise.impl.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ExerciseCreateRequest(
    val category: Int,
    val muscles: List<Int>,
    val musclesSecondary: List<Int>,
    val equipment: List<Int>,
    val variations: Int?,
    val licenseAuthor: String?
)
