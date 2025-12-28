package com.example.fuelfit.exercise.impl.presentation.detail.mvi

import com.example.fuelfit.exercise.api.model.ExerciseInfo

internal sealed interface ExerciseDetailMsg {
    object Loading : ExerciseDetailMsg
    data class Error(val message: String) : ExerciseDetailMsg
    data class Loaded(val detail: ExerciseInfo) : ExerciseDetailMsg
}
