package com.example.fuelfit.routine.impl.list.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.fuelfit.designsystem.components.FuelFitLazyColumn
import com.example.fuelfit.designsystem.components.FuelFitSwipeToDeleteListCard
import com.example.fuelfit.designsystem.components.FuelFitText
import com.example.fuelfit.routine.api.common.Routine
import com.example.fuelfit.routine.impl.R
import com.example.fuelfit.routine.impl.list.presentation.mvi.RoutinesIntent
import com.example.fuelfit.routine.impl.list.presentation.mvi.RoutinesState

@Composable
internal fun RoutinesListContent(
    state: RoutinesState,
    onIntent: (RoutinesIntent) -> Unit
) {
    val listState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        FuelFitText.HeadlineMedium(text = stringResource(R.string.routines_title))

        Spacer(modifier = Modifier.height(16.dp))

        if (state.routines.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                FuelFitText.BodyMedium(text = stringResource(R.string.routines_empty))
            }
        } else {
            FuelFitLazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = listState,
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(
                    items = state.routines,
                    key = { it.id }
                ) { routine ->
                    RoutineItem(
                        routine = routine,
                        onClick = { onIntent(RoutinesIntent.RoutineClicked(routine.id)) },
                        onEdit = { onIntent(RoutinesIntent.EditRoutine(routine.id)) },
                        onDelete = { onIntent(RoutinesIntent.DeleteRoutine(routine.id)) }
                    )
                }
            }
        }
    }
}

@Composable
private fun RoutineItem(
    routine: Routine,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    FuelFitSwipeToDeleteListCard(
        onClick = onClick,
        onDelete = onDelete
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                FuelFitText.BodyLarge(
                    text = routine.name,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                FuelFitText.BodySmall(
                    text = "${routine.start} – ${routine.end}",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(onClick = onEdit) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Routine"
                )
            }
        }
    }
}

