package com.example.fuelfit.routine.impl.create.presentation.mvi

import java.time.LocalDate

internal sealed interface CreateRoutineMsg {
    data class SetName(val value: String) : CreateRoutineMsg
    data class SetDescription(val value: String) : CreateRoutineMsg
    data class SetStartDate(val date: LocalDate) : CreateRoutineMsg
    data class SetEndDate(val date: LocalDate) : CreateRoutineMsg
    data class SetFitInWeek(val value: Boolean) : CreateRoutineMsg
    object Saving : CreateRoutineMsg
    data class Error(val message: String) : CreateRoutineMsg
}
