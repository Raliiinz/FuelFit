package com.example.fuelfit.navigation.tabs

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.example.fuelfit.exercise.impl.navigation.ExercisesTabComponent
import com.example.fuelfit.food.impl.FoodComponent
import com.example.fuelfit.workoutsession.impl.WorkoutSessionComponent

class DefaultTabsComponent(
    componentContext: ComponentContext
) : TabsComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<TabsComponent.Config>()

    private val _stack: Value<ChildStack<*, TabsComponent.Child>> =
        childStack(
            source = navigation,
            serializer = TabsComponent.Config.serializer(),
            initialStack = { listOf(TabsComponent.Config.Workout) },
            childFactory = ::createChild
        )

    override val stack: Value<ChildStack<*, TabsComponent.Child>> = _stack

    private fun createChild(
        config: TabsComponent.Config,
        childContext: ComponentContext
    ): TabsComponent.Child = when (config) {
        TabsComponent.Config.Exercises -> TabsComponent.Child.ExercisesChild(
            ExercisesTabComponent(childContext)
        )
        TabsComponent.Config.Workout -> TabsComponent.Child.WorkoutChild(
            WorkoutSessionComponent(childContext)
        )
        TabsComponent.Config.Food -> TabsComponent.Child.FoodChild(
            FoodComponent(childContext)
        )
    }

    override fun onTabClicked(tab: TabsComponent.Config) {
        navigation.bringToFront(tab)
    }
}
