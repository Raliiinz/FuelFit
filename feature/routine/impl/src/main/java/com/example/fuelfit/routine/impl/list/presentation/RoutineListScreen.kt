package com.example.fuelfit.routine.impl.list.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.fuelfit.routine.impl.list.presentation.components.RoutineItem
import com.example.fuelfit.routine.impl.list.presentation.mvi.RoutineIntent
import com.example.fuelfit.utils.LaunchedEffectAndCollect

@Composable
internal fun RoutineListScreen(component: RoutineListComponent) {
    val state by component.state.subscribeAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffectAndCollect(
        flow = component.snackbarFlow,
        lifecycle = component.lifecycle
    ) { msg ->
        snackbarHostState.showSnackbar(msg)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            when {
                state.isLoading -> CircularProgressIndicator()
                state.error != null -> Text(state.error!!)
                state.routines.isEmpty() -> Text("Тренировок пока нет")
                else -> LazyColumn {
                    items(state.routines) { routine ->
                        RoutineItem(
                            routine = routine,
                            onClick = { component.onIntent(RoutineIntent.RoutineClicked(routine.id)) },
                            onDelete = { component.onIntent(RoutineIntent.DeleteRoutine(routine.id)) }
                        )
                        Spacer(Modifier.height(8.dp))
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = { component.onIntent(RoutineIntent.CreateRoutineClicked) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
//            Icon(
////                imageVector = Icons.Default.Add,
//                contentDescription = "Создать тренировку"
//            )
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

