package com.example.fuelfit.navigation.tabs

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.fuelfit.exercise.impl.navigation.ExercisesScreen
import com.example.fuelfit.profile.impl.presentation.UserProfileScreen
import com.example.fuelfit.routine.impl.navigation.RoutineScreen

@Composable
fun TabsScreen(component: TabsComponent) {
    val stack by component.stack.subscribeAsState()

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = stack.active.instance is TabsComponent.Child.ExercisesChild,
                    onClick = { component.onTabClicked(TabsComponent.Config.Exercises) },
                    label = { Text("Exercises") },
                    icon = { Icon(Icons.Default.List, null) }
                )
                NavigationBarItem(
                    selected = stack.active.instance is TabsComponent.Child.RoutineChild,
                    onClick = { component.onTabClicked(TabsComponent.Config.Routine) },
                    label = { Text("Workout") },
                    icon = { Icon(Icons.Default.FitnessCenter, null) }
                )
                NavigationBarItem(
                    selected = stack.active.instance is TabsComponent.Child.UserProfileChild,
                    onClick = { component.onTabClicked(TabsComponent.Config.UserProfile) },
                    label = { Text("Food") },
                    icon = { Icon(Icons.Default.Fastfood, null) }
                )
            }
        }
    ) { padding ->
        Children(
            stack = stack,
            modifier = Modifier.padding(padding)
        ) {
            when (val child = it.instance) {
                is TabsComponent.Child.ExercisesChild -> ExercisesScreen(child.component)
                is TabsComponent.Child.RoutineChild -> RoutineScreen(child.component)
                is TabsComponent.Child.UserProfileChild -> UserProfileScreen(child.component)
            }
        }
    }
}
