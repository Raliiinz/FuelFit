package com.example.fuelfit.food.impl

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.MutableValue

class FoodComponent(
    componentContext: ComponentContext
) {

    val state: Value<State> = MutableValue(State("Food Screen"))

    data class State(
        val message: String
    )
}

