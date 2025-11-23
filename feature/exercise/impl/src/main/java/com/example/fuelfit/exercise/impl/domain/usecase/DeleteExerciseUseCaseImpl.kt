package com.example.fuelfit.exercise.impl.domain.usecase

import com.example.fuelfit.exercise.api.repository.ExerciseRepository
import com.example.fuelfit.exercise.api.usecase.DeleteExerciseUseCase

class DeleteExerciseUseCaseImpl(
    private val repository: ExerciseRepository
) : DeleteExerciseUseCase {
    override suspend fun invoke(id: Int) {
        repository.deleteExercise(id)
    }
}
