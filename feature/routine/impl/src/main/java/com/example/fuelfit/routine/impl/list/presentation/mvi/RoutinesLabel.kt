package com.example.fuelfit.routine.impl.list.presentation.mvi

internal sealed interface RoutinesLabel {
    data class ShowError(val message: String) : RoutinesLabel
    data class NavigateToRoutine(val id: Int) : RoutinesLabel
    object NavigateToCreateRoutine : RoutinesLabel
    data class NavigateToEditRoutine(val id: Int) : RoutinesLabel
}
