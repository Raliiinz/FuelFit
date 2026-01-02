package com.example.fuelfit.routine.impl.details.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.fuelfit.routine.api.details.model.RoutineDay
import com.example.fuelfit.routine.api.details.model.RoutineDayRequest
import com.example.fuelfit.routine.api.details.model.RoutineDayType

@Composable
fun RoutineDayDialog(
    day: RoutineDay? = null,
    onDismiss: () -> Unit,
    onSave: (RoutineDayRequest) -> Unit
) {
    var name by remember { mutableStateOf(day?.name.orEmpty()) }
    var description by remember { mutableStateOf(day?.description.orEmpty()) }
    var order by remember { mutableStateOf(day?.order?.toString() ?: "0") }
    var isRest by remember { mutableStateOf(day?.isRest ?: false) }
    var needLogs by remember { mutableStateOf(day?.needLogsToAdvance ?: false) }
    var type by remember { mutableStateOf(day?.type ?: RoutineDayType.CUSTOM) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (day == null) "Создать день" else "Редактировать день") },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { if (it.length <= 20) name = it },
                    label = { Text("Имя") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { if (it.length <= 1000) description = it },
                    label = { Text("Описание") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = order,
                    onValueChange = { if (it.all { c -> c.isDigit() }) order = it },
                    label = { Text("Порядок") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row {
                        Checkbox(checked = isRest, onCheckedChange = { isRest = it })
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Отдых")
                    }

                    Row {
                        Checkbox(checked = needLogs, onCheckedChange = { needLogs = it })
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Нужны логи для продвижения")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                DropdownMenuBox(
                    selectedType = type,
                    onTypeSelected = { type = it }
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                val dayRequest = RoutineDayRequest(
                    routineId = day?.routineId ?: 0,
                    order = order.toIntOrNull() ?: 0,
                    name = name,
                    description = description.takeIf { it.isNotBlank() },
                    isRest = isRest,
                    needLogsToAdvance = needLogs,
                    type = type,
                    config = null
                )
                onSave(dayRequest)
            }) {
                Text("Сохранить")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    )
}

@Composable
private fun DropdownMenuBox(
    selectedType: RoutineDayType,
    onTypeSelected: (RoutineDayType) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        OutlinedButton(onClick = { expanded = true }) {
            Text(selectedType.name)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            RoutineDayType.values().forEach { type ->
                DropdownMenuItem(
                    text = { Text(type.name) },
                    onClick = {
                        onTypeSelected(type)
                        expanded = false
                    }
                )
            }
        }
    }
}
