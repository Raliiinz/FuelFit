package com.example.fuelfit.routine.impl.dayDetail.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.fuelfit.designsystem.ErrorContent
import com.example.fuelfit.designsystem.LoadingContent
import com.example.fuelfit.routine.api.dayDetail.model.SlotRequest
import com.example.fuelfit.routine.impl.dayDetail.presentation.components.ExerciseSearchSheet
import com.example.fuelfit.routine.impl.dayDetail.presentation.components.SlotsList
import com.example.fuelfit.routine.impl.dayDetail.presentation.mvi.DayDetailIntent
import com.example.fuelfit.utils.flow.LaunchedEffectAndCollectAlways

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DayDetailScreen(component: DayDetailComponent) {
    val state by component.state.subscribeAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var searchSlotId by remember { mutableStateOf<Int?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffectAndCollectAlways(
        flow = component.snackbarFlow,
        lifecycle = component.lifecycle
    ) { msg ->
        snackbarHostState.currentSnackbarData?.dismiss()
        snackbarHostState.showSnackbar(msg)
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier.fillMaxSize()) {
            when {
                state.isLoading -> LoadingContent()
                state.error != null -> ErrorContent(
                    message = state.error!!,
                    onRetry = { component.onIntent(DayDetailIntent.Refresh) },
                )
                else -> SlotsList(
                    slots = state.slots.sortedBy { it.order },
                    entriesBySlot = state.entriesBySlot,
                    exerciseNamesById = state.exerciseNamesById,
                    onBackClicked = { component.onIntent(DayDetailIntent.BackClicked) },
                    onDeleteSlot = { component.onIntent(DayDetailIntent.DeleteSlot(it)) },
                    onAddExercise = { searchSlotId = it },
                    onDeleteEntry = { component.onIntent(DayDetailIntent.DeleteEntry(it)) },
                    onCreateSlot = {
                        val request = SlotRequest(dayId = state.dayId, order = state.slots.size)
                        component.onIntent(DayDetailIntent.CreateSlot(request))
                    },
                    onUpdateSlot = { slotId, request ->
                        component.onIntent(DayDetailIntent.UpdateSlot(slotId, request))
                    },
                    onUpdateEntry = { entryId, request ->
                        component.onIntent(DayDetailIntent.UpdateEntry(entryId, request))
                    },
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

    if (searchSlotId != null) {
        ModalBottomSheet(
            onDismissRequest = { searchSlotId = null },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.background,
        ) {
            ExerciseSearchSheet(
                query = state.searchQuery,
                isSearching = state.isSearching,
                results = state.searchResults,
                onQueryChange = { component.onIntent(DayDetailIntent.SearchExercises(it)) },
                onExerciseClick = { exercise ->
                    component.onIntent(
                        DayDetailIntent.SelectExercise(
                            slotId = searchSlotId!!,
                            exerciseId = exercise.baseId,
                            exerciseName = exercise.name
                        )
                    )
                    searchSlotId = null
                }
            )
        }
    }
}
