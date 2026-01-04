package com.example.fuelfit.navigation.tabs

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.example.fuelfit.exercise.impl.navigation.ExercisesTabComponent
import com.example.fuelfit.profile.impl.presentation.UserProfileComponent
import com.example.fuelfit.routine.impl.navigation.RoutineTabComponent
import kotlinx.serialization.Serializable

interface TabsComponent {

    val stack: Value<ChildStack<*, Child>>

    fun onTabClicked(tab: Config)

    sealed class Child {
        data class ExercisesChild(val component: ExercisesTabComponent) : Child()
        data class RoutineChild(val component: RoutineTabComponent) : Child()
        data class UserProfileChild(val component: UserProfileComponent) : Child()
    }

    @Serializable
    sealed interface Config {
        @Serializable data object Exercises : Config
        @Serializable data object Routine : Config
        @Serializable data object UserProfile : Config
    }
}
