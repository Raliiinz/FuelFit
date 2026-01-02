package com.example.fuelfit.routine.impl.details.presentation.mvi

internal sealed interface RoutineDayAction {
    data object Init : RoutineDayAction
}