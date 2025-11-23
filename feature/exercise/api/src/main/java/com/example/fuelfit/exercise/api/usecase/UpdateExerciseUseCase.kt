package com.example.fuelfit.exercise.api.usecase

import com.example.fuelfit.exercise.api.model.Exercise
import com.example.fuelfit.exercise.api.model.ExerciseUpdate

interface UpdateExerciseUseCase {
    suspend operator fun invoke(id: Int, body: ExerciseUpdate): Exercise
}
