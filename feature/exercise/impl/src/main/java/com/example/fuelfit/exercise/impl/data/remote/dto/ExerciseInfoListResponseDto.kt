package com.example.fuelfit.exercise.impl.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ExerciseInfoListResponseDto(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<ExerciseInfoDto>
)
