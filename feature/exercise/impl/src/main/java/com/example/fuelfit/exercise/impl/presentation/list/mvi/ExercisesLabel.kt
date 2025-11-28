package com.example.fuelfit.exercise.impl.presentation.list.mvi

internal sealed interface ExercisesLabel {
    data class ShowError(val message: String) : ExercisesLabel
}
