package com.example.fuelfit.routine.api.details.repository

import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.routine.api.details.model.RoutineDay
import com.example.fuelfit.routine.api.details.model.RoutineDayRequest

interface RoutineDayRepository {

    suspend fun getDaysByRoutine(routineId: Int): ResultWrapper<List<RoutineDay>>

    suspend fun getDay(id: Int): ResultWrapper<RoutineDay>

    suspend fun createDay(dayRequest: RoutineDayRequest): ResultWrapper<RoutineDay>

    suspend fun updateDay(day: RoutineDay): ResultWrapper<RoutineDay>
    suspend fun updateDayFromRequest(dayRequest: RoutineDayRequest, id: Int): ResultWrapper<RoutineDay>

    suspend fun deleteDay(id: Int): ResultWrapper<Unit>
}

