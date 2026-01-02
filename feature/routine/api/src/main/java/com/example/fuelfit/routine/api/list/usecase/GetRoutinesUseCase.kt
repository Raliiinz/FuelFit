package com.example.fuelfit.routine.api.list.usecase

import com.example.fuelfit.routine.api.common.Routine

interface GetRoutinesUseCase {
    suspend operator fun invoke(): List<Routine>
}
