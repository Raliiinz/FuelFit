package com.example.fuelfit.routine.impl.list.presentation.mvi

import com.example.fuelfit.routine.api.common.Routine

internal sealed interface RoutineMsg {
    object Loading : RoutineMsg
    data class Error(val message: String) : RoutineMsg
    data class SetRoutines(val routines: List<Routine>) : RoutineMsg
    data class RemoveRoutine(val id: Int) : RoutineMsg
}
