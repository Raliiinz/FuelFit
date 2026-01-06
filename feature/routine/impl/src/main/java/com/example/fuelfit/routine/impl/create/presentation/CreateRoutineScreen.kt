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
import com.example.fuelfit.routine.impl.create.presentation.components.Content
import com.example.fuelfit.routine.impl.create.presentation.mvi.CreateRoutineIntent
import com.example.fuelfit.utils.flow.LaunchedEffectAndCollectAlways

@Composable
internal fun CreateRoutineScreen(
    component: CreateRoutineComponent
) {
    val state by component.state.subscribeAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffectAndCollectAlways(
        flow = component.snackbarFlow,
        lifecycle = component.lifecycle
    ) { msg ->
        snackbarHostState.currentSnackbarData?.dismiss()
        snackbarHostState.showSnackbar(msg)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when {
            state.isLoading || state.isSaving -> LoadingContent()

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
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
        )
    }
}
