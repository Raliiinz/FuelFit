package com.example.fuelfit.routine.impl.list.presentation.mvi

import com.example.fuelfit.routine.api.common.Routine

internal sealed interface RoutinesMsg {
    object Loading : RoutinesMsg
    data class Error(val message: String) : RoutinesMsg
    data class SetRoutines(val routines: List<Routine>) : RoutinesMsg
    data class RemoveRoutine(val id: Int) : RoutinesMsg
}
