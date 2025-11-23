package com.example.fuelfit.exercise.api.usecase

interface DeleteExerciseUseCase {
    suspend operator fun invoke(id: Int): Unit
}
