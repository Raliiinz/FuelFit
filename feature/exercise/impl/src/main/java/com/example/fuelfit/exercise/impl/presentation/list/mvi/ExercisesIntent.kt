package com.example.fuelfit.exercise.impl.presentation.list.mvi

internal sealed interface ExercisesIntent {
    data class SearchQueryChanged(val query: String) : ExercisesIntent
    object Refresh : ExercisesIntent
    object LoadCategories : ExercisesIntent
    data class CategoryToggled(val categoryId: Int, val isChecked: Boolean) : ExercisesIntent
    object LoadNextPage : ExercisesIntent
    data class ExerciseClicked(val id: Int) : ExercisesIntent
}
