package com.example.fuelfit.routine.impl.create.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.fuelfit.designsystem.ErrorContent
import com.example.fuelfit.designsystem.LoadingContent
import com.example.fuelfit.routine.impl.create.presentation.mvi.CreateRoutineIntent
import com.example.fuelfit.routine.impl.create.presentation.mvi.CreateRoutineState

@Composable
internal fun CreateRoutineScreen(
    component: CreateRoutineComponent
) {
    val state by component.state.subscribeAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when {
            state.isSaving -> {
                LoadingContent()
            }

            state.error != null -> {
                ErrorContent(
                    message = state.error!!,
                    onRetry = {
                        component.onIntent(CreateRoutineIntent.Save)
                    }
                )
            }

            else -> {
                Content(
                    state = state,
                    onIntent = component::onIntent
                )
            }
        }

        SnackbarHost(
            modifier = Modifier.align(Alignment.BottomCenter),
            hostState = snackbarHostState
        )
    }
}

@Composable
private fun Content(
    state: CreateRoutineState,
    onIntent: (CreateRoutineIntent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Новая тренировка",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = state.name,
            onValueChange = {
                onIntent(CreateRoutineIntent.NameChanged(it))
            },
            label = { Text("Имя") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = state.description,
            onValueChange = {
                onIntent(CreateRoutineIntent.DescriptionChanged(it))
            },
            label = { Text("Описание") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Text(text = "Дата начала: ${state.startDate}")
        Text(text = "Дата окончания: ${state.endDate}")

        Spacer(Modifier.height(12.dp))

        Row(verticalAlignment = Alignment.Top) {
            Checkbox(
                checked = state.fitInWeek,
                onCheckedChange = {
                    onIntent(CreateRoutineIntent.FitInWeekChanged(it))
                }
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "Fit in week\n" +
                        "If enabled, the days will repeat in a weekly cycle, " +
                        "otherwise the days will follow sequentially."
            )
        }

        Spacer(Modifier.weight(1f))

        Button(
            onClick = { onIntent(CreateRoutineIntent.Save) },
            enabled = state.canSave,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Сохранить")
        }
    }
}
