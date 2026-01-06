package com.example.fuelfit.routine.impl.dayDetail.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.fuelfit.designsystem.components.FuelFitListCard
import com.example.fuelfit.designsystem.components.FuelFitText
import com.example.fuelfit.designsystem.components.FuelFitTextField
import com.example.fuelfit.routine.api.dayDetail.model.SlotEntry
import com.example.fuelfit.routine.api.dayDetail.model.SlotEntryRequest
import com.example.fuelfit.routine.impl.R

@Composable
internal fun ExpandableEntryItem(
    entry: SlotEntry,
    exerciseNamesById: Map<Int, String>,
    isExpanded: Boolean,
    onExpand: () -> Unit,
    onCollapse: () -> Unit,
    onSave: (SlotEntryRequest) -> Unit,
    onDelete: () -> Unit
) {
    val exerciseName = exerciseNamesById[entry.exerciseId] ?: "Exercise #${entry.exerciseId}"

    var sets by remember { mutableStateOf(entry.repetitionUnit?.toString().orEmpty()) }
    var reps by remember { mutableStateOf(entry.repetitionRounding.orEmpty()) }
    var weight by remember { mutableStateOf(entry.weightUnit?.toString().orEmpty()) }

    FuelFitListCard(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        elevation = 0.dp,
        onClick = onExpand
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FuelFitText.BodyLarge(
                text = stringResource(
                    R.string.exercise_item_title,
                    exerciseName
                ),
                modifier = Modifier.weight(1f)
            )

            IconButton(onClick = onExpand) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.action_edit)
                )
            }

            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(R.string.action_delete)
                )
            }
        }

        if (isExpanded) {
            Spacer(modifier = Modifier.height(12.dp))

            FuelFitTextField.Outlined(
                value = sets,
                onValueChange = { sets = it },
                label = stringResource(R.string.label_sets)
            )

            FuelFitTextField.Outlined(
                value = reps,
                onValueChange = { reps = it },
                label = stringResource(R.string.label_reps)
            )

            FuelFitTextField.Outlined(
                value = weight,
                onValueChange = { weight = it },
                label = stringResource(R.string.label_weight)
            )

            Spacer(modifier = Modifier.height(12.dp))

            FuelFitButton.Primary(
                text = stringResource(R.string.action_save),
                onClick = {
                    onSave(
                        SlotEntryRequest(
                            slotId = entry.slotId,
                            exerciseId = entry.exerciseId,
                            type = entry.type,
                            repetitionUnit = sets.toIntOrNull(),
                            repetitionRounding = reps.ifBlank { null },
                            weightUnit = weight.toIntOrNull(),
                            order = entry.order
                        )
                    )
                    onCollapse()
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
