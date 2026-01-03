package com.example.fuelfit.routine.impl.details.presentation.mvi

import com.example.fuelfit.routine.api.details.model.RoutineDay
import com.example.fuelfit.routine.api.details.model.RoutineDayRequest

internal sealed interface RoutineDayIntent {
    object Init : RoutineDayIntent
    object Refresh : RoutineDayIntent
    data class CreateDay(val day: RoutineDayRequest) : RoutineDayIntent
    data class UpdateDay(val dayRequest: RoutineDayRequest, val id: Int) : RoutineDayIntent
    data class DeleteDay(val id: Int) : RoutineDayIntent
    data class DayClicked(val day: RoutineDay) : RoutineDayIntent
}
