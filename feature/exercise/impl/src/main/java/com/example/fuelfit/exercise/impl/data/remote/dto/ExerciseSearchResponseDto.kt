package com.example.fuelfit.exercise.impl.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ExerciseSearchResponseDto(
    val suggestions: List<ExerciseSearchSuggestionDto>
)