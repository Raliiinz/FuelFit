package com.example.fuelfit.routine.impl.list.presentation.mvi

internal sealed interface RoutineIntent {
    object Load : RoutineIntent
    object Refresh : RoutineIntent
    data class RoutineClicked(val id: Int) : RoutineIntent
    data class DeleteRoutine(val id: Int) : RoutineIntent
    object CreateRoutineClicked : RoutineIntent
}
