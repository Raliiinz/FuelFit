package com.example.fuelfit.routine.impl.dayDetail.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fuelfit.exercise.api.model.ExerciseSearchItem

@Composable
internal fun ExerciseSearchSheet(
    query: String,
    isSearching: Boolean,
    results: List<ExerciseSearchItem>,
    onQueryChange: (String) -> Unit,
    onExerciseClick: (ExerciseSearchItem) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Text(
            text = "Поиск упражнения",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Введите название") }
        )

        Spacer(modifier = Modifier.height(12.dp))

        when {
            isSearching -> {
                CircularProgressIndicator()
            }

            results.isEmpty() && query.isNotBlank() -> {
                Text(
                    "Ничего не найдено",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            results.isNotEmpty() -> {
                LazyColumn(
                    modifier = Modifier.fillMaxHeight()
                ) {
                    items(results.withIndex().toList(), key = { "${it.index}_${it.value.id}_${it.value.baseId}" }) { indexedExercise ->
                        val exercise = indexedExercise.value
                        ListItem(
                            headlineContent = { Text(exercise.name) },
                            supportingContent = { Text(exercise.category) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onExerciseClick(exercise) }
                        )
                        Divider()
                    }
                }
            }
        }
    }
}
