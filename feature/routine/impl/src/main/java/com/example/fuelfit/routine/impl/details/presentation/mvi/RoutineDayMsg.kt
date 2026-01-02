package com.example.fuelfit.routine.impl.details.presentation.mvi

import com.example.fuelfit.routine.api.details.model.RoutineDay

internal sealed interface RoutineDayMsg {
    object Loading : RoutineDayMsg
    data class SetDays(val days: List<RoutineDay>) : RoutineDayMsg
    data class Error(val message: String) : RoutineDayMsg
    data class DayCreated(val day: RoutineDay) : RoutineDayMsg
    data class DayUpdated(val day: RoutineDay) : RoutineDayMsg
    data class DayDeleted(val id: Int) : RoutineDayMsg
}
