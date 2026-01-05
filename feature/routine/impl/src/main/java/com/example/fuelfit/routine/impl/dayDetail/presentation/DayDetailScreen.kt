package com.example.fuelfit.routine.impl.dayDetail.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.fuelfit.designsystem.ErrorContent
import com.example.fuelfit.designsystem.LoadingContent
import com.example.fuelfit.routine.api.dayDetail.model.SlotEntry
import com.example.fuelfit.routine.api.dayDetail.model.SlotRequest
import com.example.fuelfit.routine.impl.dayDetail.presentation.components.ExerciseSearchSheet
import com.example.fuelfit.routine.impl.dayDetail.presentation.components.SlotsList
import com.example.fuelfit.routine.impl.dayDetail.presentation.mvi.DayDetailIntent
import com.example.fuelfit.utils.flow.LaunchedEffectAndCollect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DayDetailScreen(component: DayDetailComponent) {
    val state by component.state.subscribeAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var searchSlotId by remember { mutableStateOf<Int?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var editingEntry by remember { mutableStateOf<Pair<SlotEntry?, Int>?>(null) }

    LaunchedEffectAndCollect(component.snackbarFlow, component.lifecycle) {
        snackbarHostState.showSnackbar(it)
    }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            when {
                state.isLoading -> LoadingContent()
                state.error != null -> ErrorContent(state.error!!) { component.onIntent(DayDetailIntent.Refresh) }
                state.slots.isEmpty() -> EmptyDayView(
                    onCreateSlot = {
                        val request = SlotRequest(dayId = state.dayId, order = 0)
                        component.onIntent(DayDetailIntent.CreateSlot(request))
                    }
                )
                else -> SlotsList(
                    slots = state.slots.sortedBy { it.order },
                    entriesBySlot = state.entriesBySlot,
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
                        component.onIntent(
                            DayDetailIntent.UpdateEntry(entryId, request)
                        )
                    }
                )
            }
        }
    }

    // Bottom sheet поиска упражнения
    if (searchSlotId != null) {
        ModalBottomSheet(onDismissRequest = { searchSlotId = null }, sheetState = sheetState) {
            ExerciseSearchSheet(
                query = state.searchQuery,
                isSearching = state.isSearching,
                results = state.searchResults,
                onQueryChange = { component.onIntent(DayDetailIntent.SearchExercises(it)) },
                onExerciseClick = { exercise ->
                    component.onIntent(
                        DayDetailIntent.SelectExercise(
                            slotId = searchSlotId!!,
                            exerciseId = exercise.baseId
                        )
                    )
                    searchSlotId = null
                }
            )
        }
    }
}

@Composable
private fun EmptyDayView(onCreateSlot: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Вы можете создать новый блок упражнений", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onCreateSlot, modifier = Modifier.fillMaxWidth()) {
            Text("Создать блок упражнений")
        }
    }
}
