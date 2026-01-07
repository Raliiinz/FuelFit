package com.example.fuelfit.navigation.tabs

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.fuelfit.exercise.impl.navigation.ExercisesScreen
import com.example.fuelfit.profile.impl.presentation.UserProfileScreen
import com.example.fuelfit.routine.impl.navigation.RoutineScreen
import com.example.fuelfit.app.R

@Composable
fun TabsScreen(component: TabsComponent) {
    val stack by component.stack.subscribeAsState()

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = stack.active.instance is TabsComponent.Child.ExercisesChild,
                    onClick = { component.onTabClicked(TabsComponent.Config.Exercises) },
                    label = { Text(stringResource(R.string.tab_exercises)) },
                    icon = { Icon(Icons.Default.List, contentDescription = null) }
                )
                NavigationBarItem(
                    selected = stack.active.instance is TabsComponent.Child.RoutineChild,
                    onClick = { component.onTabClicked(TabsComponent.Config.Routine) },
                    label = { Text(stringResource(R.string.tab_workout)) },
                    icon = { Icon(Icons.Default.FitnessCenter, contentDescription = null) }
                )
                NavigationBarItem(
                    selected = stack.active.instance is TabsComponent.Child.UserProfileChild,
                    onClick = { component.onTabClicked(TabsComponent.Config.UserProfile) },
                    label = { Text(stringResource(R.string.tab_profile)) },
                    icon = { Icon(Icons.Default.Person, contentDescription = null) }
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
