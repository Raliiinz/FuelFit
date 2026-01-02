package com.example.fuelfit.routine.impl.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.fuelfit.routine.impl.details.presentation.RoutineDayScreen
import com.example.fuelfit.routine.impl.create.presentation.CreateRoutineScreen
import com.example.fuelfit.routine.impl.list.presentation.RoutineListScreen

@Composable
fun RoutineScreen(component: RoutineTabComponent) {
    val stack by component.stack.subscribeAsState()

    Children(stack = stack) {
        when (val child = it.instance) {
            is RoutineTabComponent.Child.List ->
                RoutineListScreen(child.component)

            is RoutineTabComponent.Child.Create ->
                CreateRoutineScreen(child.component)

            is RoutineTabComponent.Child.Detail ->
                RoutineDayScreen(child.component)
        }
    }
}
