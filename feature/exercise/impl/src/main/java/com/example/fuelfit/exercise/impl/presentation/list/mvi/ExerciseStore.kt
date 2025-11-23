package com.example.fuelfit.exercise.impl.presentation.list.mvi

import com.arkivanov.mvikotlin.core.store.Store

internal interface ExerciseStore :
    Store<ExerciseIntent, ExerciseState, ExerciseLabel> {

    interface Factory {
        fun create(): ExerciseStore
    }
}
