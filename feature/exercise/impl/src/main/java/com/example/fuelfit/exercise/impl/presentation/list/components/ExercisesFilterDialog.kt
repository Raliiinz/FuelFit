package com.example.fuelfit.exercise.impl.presentation.list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun ExercisesFilterDialog(
    categories: List<com.example.fuelfit.exercise.api.model.ExerciseCategory>,
    selectedCategories: Set<Int>,
    onToggle: (Int, Boolean) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Фильтры") },
        text = {
            Column {
                Text("Категории")
                Spacer(Modifier.height(8.dp))
                categories.forEach { cat ->
                    val checked = cat.id in selectedCategories
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(cat.name)
                        Switch(
                            checked = checked,
                            onCheckedChange = { onToggle(cat.id, it) }
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Готово")
            }
        }
    )
}
