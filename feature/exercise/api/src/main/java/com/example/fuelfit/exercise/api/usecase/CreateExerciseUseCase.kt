package com.example.fuelfit.exercise.api.usecase

import com.example.fuelfit.exercise.api.model.Exercise
import com.example.fuelfit.exercise.api.model.ExerciseCreate

interface CreateExerciseUseCase {
    suspend operator fun invoke(body: ExerciseCreate): Exercise
}
