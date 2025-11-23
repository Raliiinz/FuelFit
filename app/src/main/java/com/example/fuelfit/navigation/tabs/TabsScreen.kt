package com.example.fuelfit.navigation.tabs

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.fuelfit.workoutsession.impl.WorkoutSessionScreen

@Composable
fun TabsScreen(component: TabsComponent) {

    val stack by component.stack.subscribeAsState()

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = stack.active.instance is TabsComponent.Child.ExercisesChild,
                    onClick = { component.onTabClicked(TabsComponent.Tab.EXERCISES) },
                    label = { Text("Exercises") },
                    icon = { Icon(Icons.Default.List, null) }
                )
                NavigationBarItem(
                    selected = stack.active.instance is TabsComponent.Child.WorkoutChild,
                    onClick = { component.onTabClicked(TabsComponent.Tab.WORKOUT) },
                    label = { Text("Workout") },
                    icon = { Icon(Icons.Default.FitnessCenter, null) }
                )
                NavigationBarItem(
                    selected = stack.active.instance is TabsComponent.Child.FoodChild,
                    onClick = { component.onTabClicked(TabsComponent.Tab.FOOD) },
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
                is TabsComponent.Child.WorkoutChild -> WorkoutSessionScreen(child.component)
                is TabsComponent.Child.FoodChild -> FoodScreen(child.component)
            }
        }
    }
}
