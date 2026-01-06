package com.example.fuelfit.routine.impl.list.presentation.mvi

import com.arkivanov.mvikotlin.core.store.Store

internal interface RoutinesStore :
    Store<RoutinesIntent, RoutinesState, RoutinesLabel> {

    interface Factory {
        fun create(): RoutinesStore
    }
}
