package com.example.fuelfit.exercise.impl.presentation.list.mvi

internal sealed interface ExerciseLabel {
    data class ShowError(val message: String) : ExerciseLabel
}
