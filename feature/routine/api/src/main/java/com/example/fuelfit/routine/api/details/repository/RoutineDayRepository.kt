package com.example.fuelfit.routine.api.details.repository

import com.example.fuelfit.routine.api.details.model.RoutineDay
import com.example.fuelfit.routine.api.details.model.RoutineDayRequest

interface RoutineDayRepository {

    suspend fun getDaysByRoutine(routineId: Int): List<RoutineDay>

    suspend fun getDay(id: Int): RoutineDay

    suspend fun createDay(dayRequest: RoutineDayRequest): RoutineDay

    suspend fun updateDay(day: RoutineDay): RoutineDay

    suspend fun deleteDay(id: Int)
}

