package com.example.fuelfit.routine.impl.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import com.example.fuelfit.routine.impl.details.presentation.RoutineDayComponent
import com.example.fuelfit.routine.impl.create.presentation.CreateRoutineComponent
import com.example.fuelfit.routine.impl.list.presentation.RoutineListComponent
import kotlinx.serialization.Serializable

class RoutineTabComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    val stack: Value<ChildStack<Config, Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.List,
            handleBackButton = true,
            childFactory = ::child
        )

    private fun child(
        config: Config,
        context: ComponentContext
    ): Child = when (config) {

        Config.List -> Child.List(
            RoutineListComponent(
                componentContext = context,
                onRoutineClick = { id ->
                    navigation.pushNew(Config.Detail(id))
                },
                onCreateRoutineClicked = {
                    navigation.pushNew(Config.Create)
                }
            )
        )

        Config.Create -> Child.Create(
            CreateRoutineComponent(
                componentContext = context,
                onClose = {
                    navigation.pop()
                }
            )
        )

        is Config.Detail -> Child.Detail(
            RoutineDayComponent(
                componentContext = context,
                routineId = config.routineId,
                onDayClicked = { dayId ->
                    // Можно сюда добавить навигацию на отдельный экран дня, если нужно
                }
            )
        )
    }

    @Serializable
    sealed interface Config {
        @Serializable
        data object List : Config

        @Serializable
        data object Create : Config

        @Serializable
        data class Detail(val routineId: Int) : Config
    }

    sealed class Child {
        data class List(val component: RoutineListComponent) : Child()
        data class Create(val component: CreateRoutineComponent) : Child()
        data class Detail(val component: RoutineDayComponent) : Child()
    }
}
