package com.example.fuelfit.exercise.impl.presentation.detail.mvi

import com.example.fuelfit.exercise.api.model.ExerciseInfo

internal data class ExerciseDetailState(
    val isLoading: Boolean = false,
    val detail: ExerciseInfo? = null,
    val error: String? = null,
    val exerciseId: Int,
)
