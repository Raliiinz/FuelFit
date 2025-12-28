package com.example.fuelfit.navigation.tabs

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.example.fuelfit.exercise.impl.navigation.ExercisesTabComponent
import com.example.fuelfit.food.impl.FoodComponent
import com.example.fuelfit.workoutsession.impl.WorkoutSessionComponent
import kotlinx.serialization.Serializable

interface TabsComponent {

    val stack: Value<ChildStack<*, Child>>

    fun onTabClicked(tab: Config)

    sealed class Child {
        data class ExercisesChild(val component: ExercisesTabComponent): Child()
        data class WorkoutChild(val component: WorkoutSessionComponent) : Child()
        data class FoodChild(val component: FoodComponent) : Child()
    }

    @Serializable
    sealed interface Config {
        @Serializable data object Exercises : Config
        @Serializable data object Workout : Config
        @Serializable data object Food : Config
    }
}
