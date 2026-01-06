package com.example.fuelfit.routine.impl.details.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.fuelfit.designsystem.components.FuelFitLazyColumn
import com.example.fuelfit.designsystem.components.FuelFitListCard
import com.example.fuelfit.designsystem.components.FuelFitText
import com.example.fuelfit.routine.api.details.model.RoutineDay
import com.example.fuelfit.routine.impl.details.presentation.mvi.RoutineDayIntent
import com.example.fuelfit.routine.impl.R

@Composable
internal fun RoutineDayListContent(
    days: List<RoutineDay>,
    listState: LazyListState,
    onIntent: (RoutineDayIntent) -> Unit,
    onEdit: (RoutineDay) -> Unit,
    onBackClicked: () -> Unit
) {
    val sortedDays = remember(days) { days.sortedBy { it.order } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
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

            FuelFitText.HeadlineMedium(text = stringResource(R.string.routine_days))
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (sortedDays.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                FuelFitText.BodyMedium(text = stringResource(R.string.add_workout_or_rest_day))
            }
        } else {
            FuelFitLazyColumn(
                state = listState,
                modifier = Modifier.fillMaxWidth()
            ) {
                items(sortedDays.size) { index ->
                    val day = sortedDays[index]
                    RoutineDayItem(
                        day = day,
                        onClick = { onIntent(RoutineDayIntent.DayClicked(day)) },
                        onEdit = { onEdit(day) },
                        onDelete = { onIntent(RoutineDayIntent.DeleteDay(day.id)) }
                    )
                }
            }
        }
    }
}

@Composable
private fun RoutineDayItem(
    day: RoutineDay,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val orderTypeStatus = if (day.isRest)
        stringResource(R.string.rest)
    else
        stringResource(R.string.active)


    FuelFitListCard(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                FuelFitText.TitleMedium(day.name)
                if (!day.description.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    FuelFitText.BodyMedium(day.description!!)
                }
                Spacer(modifier = Modifier.height(4.dp))
                FuelFitText.BodySmall(orderTypeStatus)
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(onClick = onEdit) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(R.string.edit),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(R.string.delete),
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}
