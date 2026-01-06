package com.example.fuelfit.routine.impl.details.presentation.mvi

internal sealed interface RoutineDayLabel {
    data class ShowError(val message: String) : RoutineDayLabel
    data class NavigateToDayDetail(val id: Int) : RoutineDayLabel
    object NavigateBack : RoutineDayLabel
}
