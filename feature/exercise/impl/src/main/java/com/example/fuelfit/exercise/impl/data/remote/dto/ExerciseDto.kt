package com.example.fuelfit.exercise.impl.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ExerciseDto(
    val id: Int,
    val uuid: String,
    val created: String,
    val lastUpdate: String,
    val category: Int,
    val muscles: List<Int>,
    val musclesSecondary: List<Int>,
    val equipment: List<Int>,
    val variations: Int? = null,
    val licenseAuthor: String? = null
)