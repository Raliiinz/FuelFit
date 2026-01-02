package com.example.fuelfit.routine.api.list.repository

import com.example.fuelfit.routine.api.common.Routine

interface RoutineRepository {

    suspend fun getRoutines(): List<Routine>

    suspend fun getRoutine(id: Int): Routine

    suspend fun deleteRoutine(id: Int)
}
