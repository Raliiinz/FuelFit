package com.example.fuelfit.routine.impl.dayDetail.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import com.example.fuelfit.routine.api.dayDetail.model.*

@Composable
internal fun SlotsList(
    slots: List<Slot>,
    entriesBySlot: Map<Int, List<SlotEntry>>,
    onDeleteSlot: (Int) -> Unit,
    onAddExercise: (Int) -> Unit,
    onDeleteEntry: (Int) -> Unit,
    onCreateSlot: () -> Unit,
    onUpdateSlot: (Int, SlotRequest) -> Unit,
    onUpdateEntry: (Int, SlotEntryRequest) -> Unit
) {
    LazyColumn {
        items(slots, key = { it.id }) { slot ->
            val entries = entriesBySlot[slot.id].orEmpty()

            SwipeableSlotCard(
                slot = slot,
                entries = entries,
                onDeleteSlot = { onDeleteSlot(slot.id) },
                onAddExercise = { onAddExercise(slot.id) },
                onDeleteEntry = onDeleteEntry,
                onUpdateSlot = onUpdateSlot,
                onUpdateEntry = onUpdateEntry
            )
        }

        item {
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = onCreateSlot,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Создать новый блок упражнений")
            }
        }
    }
}

@Composable
private fun SwipeableSlotCard(
    slot: Slot,
    entries: List<SlotEntry>,
    onDeleteSlot: () -> Unit,
    onAddExercise: () -> Unit,
    onDeleteEntry: (Int) -> Unit,
    onUpdateSlot: (Int, SlotRequest) -> Unit,
    onUpdateEntry: (Int, SlotEntryRequest) -> Unit
) {
    val offsetX = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()
    val maxSwipe = -200f

    var expandedEntryId by remember { mutableStateOf<Int?>(null) }
    var isEditingTitle by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf(slot.comment ?: "") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Red, RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text("delete")
//            Icon(Icons.Default.Delete, null, tint = Color.White)
        }

        Card(
            modifier = Modifier
                .offset { IntOffset(offsetX.value.toInt(), 0) }
                .clip(RoundedCornerShape(8.dp))
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            scope.launch {
                                if (offsetX.value <= maxSwipe / 2) {
                                    offsetX.animateTo(maxSwipe, tween(200))
                                    onDeleteSlot()
                                } else {
                                    offsetX.animateTo(0f, tween(200))
                                }
                            }
                        },
                        onHorizontalDrag = { _, dragAmount ->
                            scope.launch {
                                val newOffset = offsetX.value + dragAmount
                                if (newOffset <= 0f) offsetX.snapTo(newOffset)
                            }
                        }
                    )
                }
        ) {
            Column(Modifier.padding(12.dp)) {

                // ───── Заголовок блока ─────
                if (isEditingTitle) {
                    TextField(
                        value = title,
                        onValueChange = { title = it },
                        singleLine = true,
                        trailingIcon = {
                            TextButton(
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
                                Text("Сохранить")
                            }
                        }
                    )
                } else {
                    Text(
                        text = title.ifBlank { "Блок упражнений" },
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.clickable { isEditingTitle = true }
                    )
                }

                Spacer(Modifier.height(8.dp))

                // ───── Упражнения ─────
                entries.forEach { entry ->
                    ExpandableEntryItem(
                        entry = entry,
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

                Button(onClick = onAddExercise, modifier = Modifier.fillMaxWidth()) {
                    Text("Добавить упражнение")
                }
            }
        }
    }
}

@Composable
private fun ExpandableEntryItem(
    entry: SlotEntry,
    isExpanded: Boolean,
    onExpand: () -> Unit,
    onCollapse: () -> Unit,
    onSave: (SlotEntryRequest) -> Unit,
    onDelete: () -> Unit
) {

    var sets by remember { mutableStateOf(entry.repetitionUnit?.toString().orEmpty()) }
    var reps by remember { mutableStateOf(entry.repetitionRounding.orEmpty()) }
    var weight by remember { mutableStateOf(entry.weightUnit?.toString().orEmpty()) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .animateContentSize()
            .clickable { onExpand() }
    ) {
        Column(Modifier.padding(8.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Упражнение ${entry.exerciseId}")

                Row {
                    IconButton(onClick = onExpand) {
                        Text("Edit")
//                        Icon(Icons.Default.Edit, null)
                    }
                    IconButton(onClick = onDelete) {
                        Text("Delete")
//                        Icon(Icons.Default.Delete, null)
                    }
                }
            }

            if (isExpanded) {
                Spacer(Modifier.height(8.dp))

                OutlinedTextField(sets, { sets = it }, label = { Text("Подходы") })
                OutlinedTextField(reps, { reps = it }, label = { Text("Повторения") })
                OutlinedTextField(weight, { weight = it }, label = { Text("Вес") })

                Spacer(Modifier.height(8.dp))

                Button(
                    onClick = {
                        onSave(
                            SlotEntryRequest(
                                slotId = entry.slotId,
                                exerciseId = entry.exerciseId,
                                type = entry.type,
                                repetitionUnit = sets.toIntOrNull(),
                                repetitionRounding = reps.ifBlank { null },
                                weightUnit = weight.toIntOrNull(),
                                order = entry.order,
                            )
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Сохранить")
                }
            }
        }
    }
}
