package com.example.fuelfit.exercise.impl.presentation.list.mvi

internal sealed interface ExerciseIntent {
    data class SearchQueryChanged(val query: String) : ExerciseIntent
    object Refresh : ExerciseIntent
}
