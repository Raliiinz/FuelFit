package com.example.fuelfit.routine.impl.dayDetail.presentation.mvi

internal sealed interface DayDetailAction {
    data object Init : DayDetailAction
}
