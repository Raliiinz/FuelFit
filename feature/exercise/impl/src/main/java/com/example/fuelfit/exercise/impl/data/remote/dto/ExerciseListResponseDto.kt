package com.example.fuelfit.exercise.impl.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ExerciseListResponseDto(
    val count: Int,
    val next: String? = null,
    val previous: String? = null,
    val results: List<ExerciseDto>
)