package com.example.fuelfit.exercise.impl.presentation.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.fuelfit.exercise.api.model.Exercise
import com.example.fuelfit.exercise.impl.presentation.list.mvi.ExerciseIntent

@Composable
fun ExerciseScreen(component: ExerciseComponent) {
    val state by component.state.subscribeAsState()
    val snackbarHost = remember { SnackbarHostState() }

    LaunchedEffect(state.error) {
        state.error?.let { snackbarHost.showSnackbar(it) }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        TextField(
            value = state.query,
            onValueChange = { component.onIntent(ExerciseIntent.SearchQueryChanged(it)) },
            label = { Text("Поиск") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                val filtered = state.exercises?.exercises
                    ?.filter { it.licenseAuthor?.contains(state.query, ignoreCase = true) == true }
                    ?: emptyList()

                items(filtered) { exercise ->
                    ExerciseItem(exercise)
                }
            }
        }
    }

    SnackbarHost(hostState = snackbarHost)
}

@Composable
fun ExerciseItem(exercise: Exercise) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        exercise.licenseAuthor?.let {
            Text(
                text = it,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
