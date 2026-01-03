package com.example.fuelfit.exercise.impl.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import com.example.fuelfit.exercise.impl.presentation.detail.ExerciseDetailComponent
import com.example.fuelfit.exercise.impl.presentation.list.ExercisesListComponent
import kotlinx.serialization.Serializable

class ExercisesTabComponent(
    componentContext: ComponentContext,
) : ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    private val _stack: Value<ChildStack<Config, Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.List,
            childFactory = ::child,
            handleBackButton = true
        )

    val stack: Value<ChildStack<Config, Child>> = _stack

    fun pushDetail(id: Int) {
        navigation.pushNew(Config.Detail(id))
    }

    private fun child(config: Config, context: ComponentContext): Child =
        when (config) {
            Config.List -> Child.ListChild(
                ExercisesListComponent(
                    context,
                    onExerciseClicked = { pushDetail(it) }
                )
            )
            is Config.Detail -> Child.DetailChild(
                ExerciseDetailComponent(
                    context,
                    config.id,
                    onBack = { navigation.pop() }
                )
            )
        }

    @Serializable
    sealed interface Config {
        @Serializable
        data object List : Config
        @Serializable
        data class Detail(val id: Int) : Config
    }

    sealed class Child {
        internal data class ListChild(val component: ExercisesListComponent) : Child()
        internal data class DetailChild(val component: ExerciseDetailComponent) : Child()
    }
}
