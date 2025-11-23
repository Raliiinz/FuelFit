package com.example.fuelfit.exercise.impl.presentation.list.mvi

import com.example.fuelfit.exercise.api.model.ExerciseList

internal sealed interface ExerciseMsg {
    data class SetExercises(val exercises: ExerciseList) : ExerciseMsg
    data class SetQuery(val query: String) : ExerciseMsg
    object Loading : ExerciseMsg
    data class Error(val message: String) : ExerciseMsg
}
