package com.example.fuelfit.exercise.impl.presentation.list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fuelfit.exercise.api.model.ExerciseInfo

@Composable
fun ExercisesList(
    exercises: List<ExerciseInfo>,
    query: String,
    listState: LazyListState,
    onExerciseClick: (Int) -> Unit
) {
    val filtered = exercises.filter {
        it.translations.firstOrNull()?.name?.contains(query, ignoreCase = true) == true
    }

    LazyColumn(state = listState) {
        items(filtered) { ex ->
            ExerciseItem(ex) {
                onExerciseClick(ex.id)
            }
        }
    }
}

@Composable
fun ExerciseItem(ex: ExerciseInfo, onClick: () -> Unit) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onClick() }
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(ex.translations.firstOrNull()?.name ?: "No name")
            Spacer(Modifier.height(4.dp))
            Text(ex.musclesLine())
        }
    }
}
