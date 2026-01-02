package com.example.fuelfit.routine.impl.list.presentation.mvi

import com.arkivanov.mvikotlin.core.store.Store

internal interface RoutineStore :
    Store<RoutineIntent, RoutineState, RoutineLabel> {

    interface Factory {
        fun create(): RoutineStore
    }
}
