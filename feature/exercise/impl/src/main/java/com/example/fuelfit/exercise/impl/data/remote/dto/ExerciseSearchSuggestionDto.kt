package com.example.fuelfit.exercise.impl.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ExerciseSearchSuggestionDto(
    val value: String,
    val data: ExerciseSearchItemDto
)