package com.example.fuelfit.routine.api.create.repository

import com.example.fuelfit.routine.api.common.RoutineRequest
import com.example.fuelfit.routine.api.common.Routine

interface CreateRoutineRepository {
    suspend fun createRoutine(request: RoutineRequest): Routine

    suspend fun updateRoutine(id: Int, request: RoutineRequest): Routine
}
