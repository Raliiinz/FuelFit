package com.example.fuelfit.routine.api.create.repository

import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.routine.api.create.model.RoutineRequest
import com.example.fuelfit.routine.api.common.Routine

interface CreateRoutineRepository {
    suspend fun createRoutine(request: RoutineRequest): ResultWrapper<Routine>

    suspend fun updateRoutine(id: Int, request: RoutineRequest): ResultWrapper<Routine>
}
