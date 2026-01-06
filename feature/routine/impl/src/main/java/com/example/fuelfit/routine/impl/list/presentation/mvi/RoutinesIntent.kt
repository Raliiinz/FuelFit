package com.example.fuelfit.routine.impl.list.presentation.mvi

internal sealed interface RoutinesIntent {
    object Load : RoutinesIntent
    object Retry : RoutinesIntent
    data class RoutineClicked(val id: Int) : RoutinesIntent
    data class DeleteRoutine(val id: Int) : RoutinesIntent
    data class EditRoutine(val id: Int) : RoutinesIntent
    object CreateRoutineClicked : RoutinesIntent
}
