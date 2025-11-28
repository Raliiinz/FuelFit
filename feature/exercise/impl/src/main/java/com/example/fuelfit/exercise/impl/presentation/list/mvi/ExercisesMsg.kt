package com.example.fuelfit.exercise.impl.presentation.list.mvi

import com.example.fuelfit.exercise.api.model.ExerciseCategory
import com.example.fuelfit.exercise.api.model.ExerciseList

internal sealed interface ExercisesMsg {
    data class SetExercises(val exercises: ExerciseList) : ExercisesMsg
    data class SetQuery(val query: String) : ExercisesMsg
    object Loading : ExercisesMsg
    data class Error(val message: String) : ExercisesMsg
    data class SetCategories(val categories: List<ExerciseCategory>) : ExercisesMsg
    data class UpdateSelectedCategories(val selected: Set<Int>) : ExercisesMsg
}
