package com.example.fuelfit.routine.impl.details.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.fuelfit.designsystem.ErrorContent
import com.example.fuelfit.designsystem.LoadingContent
import com.example.fuelfit.routine.api.details.model.RoutineDay
import com.example.fuelfit.routine.impl.details.presentation.components.RoutineDayDialog
import com.example.fuelfit.routine.impl.details.presentation.components.RoutineDayListContent
import com.example.fuelfit.routine.impl.details.presentation.mvi.RoutineDayIntent
import com.example.fuelfit.routine.impl.R
import com.example.fuelfit.utils.flow.LaunchedEffectAndCollectAlways

@Composable
internal fun RoutineDayScreen(
    component: RoutineDayComponent
) {
    val state by component.state.subscribeAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val listState = rememberLazyListState()
    var isDialogOpen by remember { mutableStateOf(false) }
    var editingDay by remember { mutableStateOf<RoutineDay?>(null) }

    LaunchedEffectAndCollectAlways(
        flow = component.snackbarFlow,
        lifecycle = component.lifecycle
    ) { msg ->
        snackbarHostState.currentSnackbarData?.dismiss()
        snackbarHostState.showSnackbar(msg)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            state.isLoading -> LoadingContent()
            state.error != null -> ErrorContent(
                message = state.error!!,
                onRetry = { component.onIntent(RoutineDayIntent.Refresh) }
            )
            else -> {
                RoutineDayListContent(
                    days = state.days,
                    listState = listState,
                    onIntent = component::onIntent,
                    onEdit = { day ->
                        editingDay = day
                        isDialogOpen = true
                    },
                    onBackClicked = {
                        component.onIntent(RoutineDayIntent.BackClicked)
                    }
                )
            }
        }

        FloatingActionButton(
            onClick = {
                editingDay = null
                isDialogOpen = true
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.add_day)
            )
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
        )
    }

    if (isDialogOpen) {
        RoutineDayDialog(
            day = editingDay,
            onDismiss = { isDialogOpen = false },
            onSave = { dayRequest ->
                if (editingDay != null) {
                    component.onIntent(RoutineDayIntent.UpdateDay(dayRequest, editingDay!!.id))
                } else {
                    component.onIntent(RoutineDayIntent.CreateDay(dayRequest))
                }
                isDialogOpen = false
                editingDay = null
            }
        )
    }
}
