package com.example.fuelfit.exercise.impl.presentation.list.mvi

import com.example.fuelfit.exercise.api.model.ExerciseList

internal data class ExerciseState(
    val exercises: ExerciseList? = null,
    val query: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)
