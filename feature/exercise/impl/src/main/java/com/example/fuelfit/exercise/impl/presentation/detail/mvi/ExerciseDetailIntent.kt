package com.example.fuelfit.exercise.impl.presentation.detail.mvi

internal sealed interface ExerciseDetailIntent {
    object Load : ExerciseDetailIntent
    object Retry : ExerciseDetailIntent
    object BackClicked : ExerciseDetailIntent
}
