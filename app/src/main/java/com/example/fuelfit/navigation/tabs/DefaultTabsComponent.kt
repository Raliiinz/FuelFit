package com.example.fuelfit.navigation.tabs

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.example.fuelfit.exercise.impl.navigation.ExercisesTabComponent
import com.example.fuelfit.profile.impl.presentation.UserProfileComponent
import com.example.fuelfit.routine.impl.navigation.RoutineTabComponent

class DefaultTabsComponent(
    componentContext: ComponentContext,
    private val onLogout: () -> Unit
) : TabsComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<TabsComponent.Config>()

    private val _stack: Value<ChildStack<*, TabsComponent.Child>> =
        childStack(
            source = navigation,
            serializer = TabsComponent.Config.serializer(),
            initialStack = { listOf(TabsComponent.Config.Routine) },
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
        TabsComponent.Config.Routine -> TabsComponent.Child.RoutineChild(
            RoutineTabComponent(childContext)
        )
        TabsComponent.Config.UserProfile -> TabsComponent.Child.UserProfileChild(
            UserProfileComponent(
                componentContext = childContext,
                onLogout = { onLogout() }
            )
        )
    }

    override fun onTabClicked(tab: TabsComponent.Config) {
        navigation.bringToFront(tab)
    }
}
