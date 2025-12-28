package com.example.fuelfit.exercise.impl.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.fuelfit.exercise.impl.presentation.detail.ExerciseDetailScreen
import com.example.fuelfit.exercise.impl.presentation.list.ExercisesListScreen

@Composable
fun ExercisesScreen(component: ExercisesTabComponent) {
    val stack by component.stack.subscribeAsState()

    Children(stack = stack) {
        when (val child = it.instance) {
            is ExercisesTabComponent.Child.ListChild -> ExercisesListScreen(child.component)
            is ExercisesTabComponent.Child.DetailChild -> ExerciseDetailScreen(child.component)
        }
    }
}
