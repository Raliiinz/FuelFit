package com.example.fuelfit.routine.impl.dayDetail.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.fuelfit.designsystem.components.FuelFitButton
import com.example.fuelfit.designsystem.components.FuelFitLazyColumn
import com.example.fuelfit.designsystem.components.FuelFitSwipeToDeleteListCard
import com.example.fuelfit.designsystem.components.FuelFitText
import com.example.fuelfit.designsystem.components.FuelFitTextField
import com.example.fuelfit.routine.api.dayDetail.model.Slot
import com.example.fuelfit.routine.api.dayDetail.model.SlotEntry
import com.example.fuelfit.routine.api.dayDetail.model.SlotEntryRequest
import com.example.fuelfit.routine.api.dayDetail.model.SlotRequest
import com.example.fuelfit.routine.impl.R

@Composable
internal fun SlotsList(
    slots: List<Slot>,
    entriesBySlot: Map<Int, List<SlotEntry>>,
    exerciseNamesById: Map<Int, String>,
    onBackClicked: () -> Unit,
    onDeleteSlot: (Int) -> Unit,
    onAddExercise: (Int) -> Unit,
    onDeleteEntry: (Int) -> Unit,
    onCreateSlot: () -> Unit,
    onUpdateSlot: (Int, SlotRequest) -> Unit,
    onUpdateEntry: (Int, SlotEntryRequest) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // ───── Заголовок экрана ─────
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBackClicked,
                modifier = Modifier.offset(x = (-8).dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            FuelFitText.HeadlineMedium(
                text = stringResource(R.string.screen_exercises_title)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ───── Контент ─────
        if (slots.isEmpty()) {
            EmptyDayView(onCreateSlot = onCreateSlot, modifier = Modifier.fillMaxSize())
        } else {
            val listState = rememberLazyListState()

            FuelFitLazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = listState
            ) {
                items(slots, key = { it.id }) { slot ->
                    val entries = entriesBySlot[slot.id].orEmpty()

                    FuelFitSwipeToDeleteListCard(
                        onDelete = { onDeleteSlot(slot.id) }
                    ) {
                        SwipeableSlotCardContent(
                            slot = slot,
                            entries = entries,
                            onAddExercise = { onAddExercise(slot.id) },
                            onDeleteEntry = onDeleteEntry,
                            onUpdateSlot = onUpdateSlot,
                            onUpdateEntry = onUpdateEntry,
                            exerciseNamesById = exerciseNamesById
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    FuelFitButton.Primary(
                        text = stringResource(R.string.create_new_slot),
                        onClick = onCreateSlot,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
private fun SwipeableSlotCardContent(
    slot: Slot,
    exerciseNamesById: Map<Int, String>,
    entries: List<SlotEntry>,
    onAddExercise: () -> Unit,
    onDeleteEntry: (Int) -> Unit,
    onUpdateSlot: (Int, SlotRequest) -> Unit,
    onUpdateEntry: (Int, SlotEntryRequest) -> Unit
) {
    var expandedEntryId by remember { mutableStateOf<Int?>(null) }
    var isEditingTitle by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf(slot.comment ?: "") }

    Column(Modifier.padding(12.dp)) {
        if (isEditingTitle) {
            FuelFitTextField.Outlined(
                value = title,
                onValueChange = { title = it },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            isEditingTitle = false
                            onUpdateSlot(
                                slot.id,
                                SlotRequest(
                                    dayId = slot.dayId,
                                    order = slot.order,
                                    comment = title
                                )
                            )
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Save",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        } else {
            FuelFitText.EditableUnderline(
                text = title.ifBlank { stringResource(R.string.slot_default_title) },
                onClick = { isEditingTitle = true }
            )
        }

        Spacer(Modifier.height(8.dp))

        entries.forEach { entry ->
            ExpandableEntryItem(
                entry = entry,
                exerciseNamesById = exerciseNamesById,
                isExpanded = expandedEntryId == entry.id,
                onExpand = { expandedEntryId = entry.id },
                onCollapse = { expandedEntryId = null },
                onSave = { request ->
                    onUpdateEntry(entry.id, request)
                    expandedEntryId = null
                },
                onDelete = { onDeleteEntry(entry.id) }
            )
        }

        Spacer(Modifier.height(8.dp))

        FuelFitButton.Secondary(
            text = stringResource(R.string.add_exercise),
            onClick = onAddExercise,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun EmptyDayView(onCreateSlot: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            FuelFitText.BodyLarge(
                text = stringResource(R.string.empty_day_message)
            )
            Spacer(modifier = Modifier.height(8.dp))
            FuelFitButton.Primary(
                text = stringResource(R.string.create_slot),
                onClick = onCreateSlot,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
