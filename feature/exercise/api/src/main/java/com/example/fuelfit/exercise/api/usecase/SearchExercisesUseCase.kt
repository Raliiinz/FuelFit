package com.example.fuelfit.exercise.api.usecase

import com.example.fuelfit.exercise.api.model.ExerciseSearchItem
import com.example.fuelfit.model.ResultWrapper

interface SearchExercisesUseCase {
    suspend operator fun invoke(
        term: String,
        language: String
    ): ResultWrapper<List<ExerciseSearchItem>>
}