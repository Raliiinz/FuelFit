package com.example.fuelfit.routine.impl.list.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.fuelfit.designsystem.ErrorContent
import com.example.fuelfit.designsystem.LoadingContent
import com.example.fuelfit.routine.impl.list.presentation.components.RoutinesListContent
import com.example.fuelfit.routine.impl.list.presentation.mvi.RoutinesIntent
import com.example.fuelfit.utils.flow.LaunchedEffectAndCollectAlways
import com.example.fuelfit.routine.impl.R

@Composable
internal fun RoutinesListScreen(
    component: RoutinesListComponent
) {
    val state by component.state.subscribeAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        component.onIntent(RoutinesIntent.Load)
    }

    LaunchedEffectAndCollectAlways(
        flow = component.snackbarFlow,
        lifecycle = component.lifecycle
    ) { msg ->
        snackbarHostState.currentSnackbarData?.dismiss()
        snackbarHostState.showSnackbar(msg)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            state.isLoading -> {
                LoadingContent()
            }

            state.error != null -> {
                ErrorContent(
                    message = state.error!!,
                    onRetry = {
                        component.onIntent(RoutinesIntent.Retry)
                    }
                )
            }

            else -> {
                RoutinesListContent(
                    state = state,
                    onIntent = component::onIntent
                )
            }
        }

        FloatingActionButton(
            onClick = {
                component.onIntent(RoutinesIntent.CreateRoutineClicked)
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(
                    R.string.create_routine
                )
            )
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
        )
    }
}
