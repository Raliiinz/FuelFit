package com.example.fuelfit.routine.impl.list.presentation.mvi

internal sealed interface RoutineLabel {
    data class ShowError(val message: String) : RoutineLabel
    data class NavigateToRoutine(val id: Int) : RoutineLabel
    object NavigateToCreateRoutine : RoutineLabel
}
