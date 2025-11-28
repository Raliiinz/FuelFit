package com.example.fuelfit.exercise.impl.presentation.list.mvi

import com.example.fuelfit.exercise.api.model.ExerciseCategory
import com.example.fuelfit.exercise.api.model.ExerciseList

internal data class ExercisesState(
    val exercises: ExerciseList? = null,
    val categories: List<ExerciseCategory> = emptyList(),
    val selectedCategories: Set<Int> = emptySet(),
    val query: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)
