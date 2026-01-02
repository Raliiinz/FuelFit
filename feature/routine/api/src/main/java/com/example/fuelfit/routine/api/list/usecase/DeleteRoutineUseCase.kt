package com.example.fuelfit.routine.api.list.usecase

interface DeleteRoutineUseCase {
    suspend operator fun invoke(id: Int)
}
