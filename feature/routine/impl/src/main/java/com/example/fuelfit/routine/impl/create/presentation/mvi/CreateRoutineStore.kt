package com.example.fuelfit.routine.impl.create.presentation.mvi

import com.arkivanov.mvikotlin.core.store.Store

internal interface CreateRoutineStore :
    Store<CreateRoutineIntent, CreateRoutineState, CreateRoutineLabel> {

    interface Factory {
        fun create(): CreateRoutineStore
    }
}
