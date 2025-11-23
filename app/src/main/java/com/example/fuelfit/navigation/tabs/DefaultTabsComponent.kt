package com.example.fuelfit.navigation.tabs

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.example.fuelfit.workoutsession.impl.WorkoutSessionComponent
import kotlinx.serialization.Serializable

class DefaultTabsComponent(
    componentContext: ComponentContext
) : TabsComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    private val _stack =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.Workout,
            childFactory = ::child,
        )

    override val stack: Value<ChildStack<*, TabsComponent.Child>> = _stack

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Exercises : Config
        @Serializable
        data object Workout : Config
        @Serializable
        data object Food : Config
    }

    private fun child(config: Config, context: ComponentContext): TabsComponent.Child =
        when (config) {
            Config.Exercises ->
                TabsComponent.Child.ExercisesChild(ExercisesComponent(context))

            Config.Workout ->
                TabsComponent.Child.WorkoutChild(WorkoutSessionComponent(context))

            Config.Food ->
                TabsComponent.Child.FoodChild(FoodComponent(context))
        }

    override fun onTabClicked(tab: TabsComponent.Tab) {
        when (tab) {
            TabsComponent.Tab.EXERCISES -> navigation.bringToFront(Config.Exercises)
            TabsComponent.Tab.WORKOUT -> navigation.bringToFront(Config.Workout)
            TabsComponent.Tab.FOOD -> navigation.bringToFront(Config.Food)
        }
    }
}
