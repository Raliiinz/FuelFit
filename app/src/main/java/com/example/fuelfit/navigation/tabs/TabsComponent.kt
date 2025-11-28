package com.example.fuelfit.navigation.tabs

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.example.fuelfit.exercise.impl.presentation.list.ExercisesComponent
import com.example.fuelfit.food.impl.FoodComponent
import com.example.fuelfit.workoutsession.impl.WorkoutSessionComponent

interface TabsComponent {

    val stack: Value<ChildStack<*, Child>>

    fun onTabClicked(tab: Tab)

    sealed class Child {
        data class ExercisesChild(val component: ExercisesComponent) : Child()
        data class WorkoutChild(val component: WorkoutSessionComponent) : Child()
        data class FoodChild(val component: FoodComponent) : Child()
    }

    enum class Tab {
        EXERCISES,
        WORKOUT,
        FOOD
    }
}
