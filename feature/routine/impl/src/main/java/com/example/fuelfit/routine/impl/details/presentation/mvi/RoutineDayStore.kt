package com.example.fuelfit.routine.impl.details.presentation.mvi

import com.arkivanov.mvikotlin.core.store.Store

internal interface RoutineDayStore : Store<RoutineDayIntent, RoutineDayState, RoutineDayLabel> {

    interface Factory {
        fun create(routineId: Int): RoutineDayStore
    }
}
