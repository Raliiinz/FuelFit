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
import com.example.fuelfit.designsystem.components.BottomNavItem
import com.example.fuelfit.designsystem.components.FuelFitBottomNavigation

@Composable
fun TabsScreen(component: TabsComponent) {
    val stack by component.stack.subscribeAsState()

    val tabs: List<BottomNavItem<TabsComponent.Config>> = listOf(
        BottomNavItem(
            id = TabsComponent.Config.Exercises,
            label = stringResource(R.string.tab_exercises),
            icon = Icons.Default.List
        ),
        BottomNavItem(
            id = TabsComponent.Config.Routine,
            label = stringResource(R.string.tab_workout),
            icon = Icons.Default.FitnessCenter
        ),
        BottomNavItem(
            id = TabsComponent.Config.UserProfile,
            label = stringResource(R.string.tab_profile),
            icon = Icons.Default.Person
        )
    )

    val currentId = when (stack.active.instance) {
        is TabsComponent.Child.ExercisesChild -> TabsComponent.Config.Exercises
        is TabsComponent.Child.RoutineChild -> TabsComponent.Config.Routine
        is TabsComponent.Child.UserProfileChild -> TabsComponent.Config.UserProfile
    }

    Scaffold(
        bottomBar = {
            FuelFitBottomNavigation(
                items = tabs,
                currentId = currentId,
                onItemSelected = { config ->
                    component.onTabClicked(config)
                }
            )
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
