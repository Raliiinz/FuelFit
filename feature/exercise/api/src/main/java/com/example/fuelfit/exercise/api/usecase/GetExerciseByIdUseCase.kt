package com.example.fuelfit.exercise.api.usecase

import com.example.fuelfit.exercise.api.model.Exercise

interface GetExerciseByIdUseCase {
    suspend operator fun invoke(id: Int): Exercise
}