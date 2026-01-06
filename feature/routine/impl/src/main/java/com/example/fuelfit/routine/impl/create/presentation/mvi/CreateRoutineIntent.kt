package com.example.fuelfit.routine.impl.create.presentation.mvi

import java.time.LocalDate

internal sealed interface CreateRoutineIntent {
    data class NameChanged(val value: String) : CreateRoutineIntent
    data class DescriptionChanged(val value: String) : CreateRoutineIntent
    data class StartDateChanged(val date: LocalDate) : CreateRoutineIntent
    data class EndDateChanged(val date: LocalDate) : CreateRoutineIntent
    data class FitInWeekChanged(val value: Boolean) : CreateRoutineIntent
    object Save : CreateRoutineIntent
    object BackClicked : CreateRoutineIntent
}
