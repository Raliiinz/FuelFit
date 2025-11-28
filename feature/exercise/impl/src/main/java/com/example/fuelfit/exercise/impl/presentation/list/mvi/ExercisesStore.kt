package com.example.fuelfit.exercise.impl.presentation.list.mvi

import com.arkivanov.mvikotlin.core.store.Store

internal interface ExercisesStore :
    Store<ExercisesIntent, ExercisesState, ExercisesLabel> {

    interface Factory {
        fun create(): ExercisesStore
    }
}
