package com.example.fuelfit.exercise.impl.presentation.detail.mvi

internal sealed interface ExerciseDetailLabel {
    data class ShowError(val message: String) : ExerciseDetailLabel
    object NavigateBack : ExerciseDetailLabel
}
