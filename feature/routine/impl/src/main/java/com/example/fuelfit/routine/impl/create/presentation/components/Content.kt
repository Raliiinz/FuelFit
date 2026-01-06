package com.example.fuelfit.routine.impl.create.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Checkbox
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
import com.example.fuelfit.designsystem.components.FuelFitDatePickerDialog
import com.example.fuelfit.designsystem.components.FuelFitText
import com.example.fuelfit.designsystem.components.FuelFitTextField
import com.example.fuelfit.routine.impl.create.presentation.mvi.CreateRoutineIntent
import com.example.fuelfit.routine.impl.create.presentation.mvi.CreateRoutineState
import java.time.format.DateTimeFormatter
import com.example.fuelfit.routine.impl.R
import com.example.fuelfit.utils.datetime.toDate
import com.example.fuelfit.utils.datetime.toLocalDate

@Composable
internal fun Content(
    state: CreateRoutineState,
    onIntent: (CreateRoutineIntent) -> Unit
) {
    val dateFormatter = remember {
        DateTimeFormatter.ofPattern("dd MMM yyyy")
    }
    var showStartDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { onIntent(CreateRoutineIntent.BackClicked) },
                modifier = Modifier.offset(x = (-8).dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            FuelFitText.HeadlineMedium(
                text = stringResource(R.string.create_routine_title)
            )
        }

        Spacer(Modifier.height(16.dp))

        FuelFitTextField.Outlined(
            value = state.name,
            onValueChange = { onIntent(CreateRoutineIntent.NameChanged(it)) },
            label = stringResource(R.string.routine_name_label),
        )

        FuelFitTextField.Outlined(
            value = state.description,
            onValueChange = { onIntent(CreateRoutineIntent.DescriptionChanged(it)) },
            label = stringResource(R.string.routine_description_label),
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FuelFitTextField.Outlined(
                value = state.startDate.format(dateFormatter),
                onValueChange = {},
                label = stringResource(R.string.routine_start_date),
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { showStartDatePicker = true }) {
                        Icon(
                            Icons.Default.CalendarToday,
                            contentDescription = stringResource(R.string.routine_pick_date)
                        )
                    }
                },
                modifier = Modifier.weight(1f)
            )

            FuelFitTextField.Outlined(
                value = state.endDate.format(dateFormatter),
                onValueChange = {},
                label = stringResource(R.string.routine_end_date),
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { showEndDatePicker = true }) {
                        Icon(
                            Icons.Default.CalendarToday,
                            contentDescription = stringResource(R.string.routine_pick_date)
                        )
                    }
                },
                modifier = Modifier.weight(1f)
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = state.fitInWeek,
                onCheckedChange = {
                    onIntent(CreateRoutineIntent.FitInWeekChanged(it))
                }
            )
            Spacer(Modifier.width(8.dp))
            FuelFitText.BodyMedium(
                text = stringResource(R.string.routine_fit_in_week)
            )
        }

        Spacer(Modifier.weight(1f))

        FuelFitButton.Primary(
            text = stringResource(R.string.routine_save),
            onClick = { onIntent(CreateRoutineIntent.Save) },
            enabled = state.canSave,
            modifier = Modifier.fillMaxWidth()
        )
    }

    if (showStartDatePicker) {
        FuelFitDatePickerDialog(
            selectedDate = state.startDate.toDate(),
            onDateSelected = { date ->
                onIntent(
                    CreateRoutineIntent.StartDateChanged(
                        date.toLocalDate()
                    )
                )
            },
            onDismiss = { showStartDatePicker = false }
        )
    }

    if (showEndDatePicker) {
        FuelFitDatePickerDialog(
            selectedDate = state.endDate.toDate(),
            onDateSelected = { date ->
                onIntent(
                    CreateRoutineIntent.EndDateChanged(
                        date.toLocalDate()
                    )
                )
            },
            onDismiss = { showEndDatePicker = false }
        )
    }
}
