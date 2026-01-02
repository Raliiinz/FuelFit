package com.example.fuelfit.routine.impl.create.data

import com.example.fuelfit.routine.api.common.RoutineRequest
import com.example.fuelfit.routine.api.create.repository.CreateRoutineRepository
import com.example.fuelfit.routine.api.common.Routine
import com.example.fuelfit.routine.impl.create.data.remote.CreateRoutineApiService
import com.example.fuelfit.routine.impl.common.toDomain
import com.example.fuelfit.routine.impl.common.toDto

class CreateRoutineRepositoryImpl(
    private val api: CreateRoutineApiService
) : CreateRoutineRepository {

    override suspend fun createRoutine(request: RoutineRequest): Routine =
        api.createRoutine(request.toDto()).toDomain()

    override suspend fun updateRoutine(
        id: Int,
        request: RoutineRequest
    ): Routine =
        api.updateRoutine(id, request.toDto()).toDomain()
}
