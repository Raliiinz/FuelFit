package com.example.fuelfit.workoutsession.impl

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.MutableValue

class WorkoutSessionComponent(
    componentContext: ComponentContext
) {

    val state: Value<State> = MutableValue(State("Workout Session Screen"))

    data class State(
        val message: String
    )
}

