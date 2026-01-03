package com.example.fuelfit.routine.impl.details.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fuelfit.routine.api.details.model.RoutineDay

@Composable
internal fun RoutineDayItem(
    day: RoutineDay,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(day.name, style = MaterialTheme.typography.titleMedium)
                if (!day.description.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(day.description!!, style = MaterialTheme.typography.bodyMedium)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Порядок: ${day.order} • Тип: ${day.type.name} • ${if (day.isRest) "Отдых" else "Активный"}",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = androidx.compose.ui.Alignment.End
            ) {
                IconButton(onClick = onEdit) {
                    Text("Редактировать")
//                    Icon(Icons.Default.Edit, contentDescription = "Редактировать")
                }
                IconButton(onClick = onDelete) {
                    Text("Удалить")
//                    Icon(Icons.Default.Delete, contentDescription = "Удалить")
                }
            }
        }
    }
}
