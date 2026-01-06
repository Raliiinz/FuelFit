package com.example.fuelfit.routine.impl.details.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import com.example.fuelfit.routine.api.details.model.RoutineDay
import com.example.fuelfit.routine.api.details.model.RoutineDayRequest
import com.example.fuelfit.designsystem.components.FuelFitFilterDialog
import com.example.fuelfit.designsystem.components.FuelFitText
import com.example.fuelfit.designsystem.components.FuelFitTextField
import com.example.fuelfit.routine.impl.R

@Composable
internal fun RoutineDayDialog(
    day: RoutineDay? = null,
    onDismiss: () -> Unit,
    onSave: (RoutineDayRequest) -> Unit
) {
    var name by remember { mutableStateOf(day?.name.orEmpty()) }
    var description by remember { mutableStateOf(day?.description.orEmpty()) }
    var order by remember { mutableStateOf(day?.order?.toString().orEmpty()) }
    var isRest by remember { mutableStateOf(day?.isRest ?: false) }

    val isSaveEnabled = name.isNotBlank() && description.isNotBlank() && order.isNotBlank() && order.all { it.isDigit() }

    FuelFitFilterDialog(
        title = if (day == null) stringResource(R.string.create_day) else stringResource(R.string.edit_day),
        confirmText = stringResource(R.string.save),
        dismissText = stringResource(R.string.cancel),
        onDismiss = onDismiss,
        onConfirm = {
            val dayRequest = RoutineDayRequest(
                routineId = day?.routineId ?: 0,
                order = order.toIntOrNull() ?: 0,
                name = name,
                description = description.takeIf { it.isNotBlank() },
                isRest = isRest
            )
            onSave(dayRequest)
        },
        confirmEnabled = isSaveEnabled
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            FuelFitTextField.Outlined(
                value = name,
                onValueChange = { if (it.length <= 20) name = it },
                label = stringResource(R.string.name)
            )

            FuelFitTextField.Outlined(
                value = description,
                onValueChange = { if (it.length <= 1000) description = it },
                label = stringResource(R.string.description)
            )

            FuelFitTextField.Outlined(
                value = order,
                onValueChange = { if (it.all { c -> c.isDigit() }) order = it },
                label = stringResource(R.string.order)
            )

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isRest,
                    onCheckedChange = { isRest = it }
                )
                Spacer(modifier = Modifier.width(8.dp))
                FuelFitText.BodyMedium(text = stringResource(R.string.rest))
            }
        }
    }
}
