package com.example.fuelfit.routine.api.list.repository

import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.routine.api.common.Routine

interface RoutineRepository {

    suspend fun getRoutines(): ResultWrapper<List<Routine>>

    suspend fun getRoutine(id: Int): ResultWrapper<Routine>

    suspend fun deleteRoutine(id: Int): ResultWrapper<Unit>
}
