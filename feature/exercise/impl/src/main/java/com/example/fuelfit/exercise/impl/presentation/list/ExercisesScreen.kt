package com.example.fuelfit.exercise.impl.presentation.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.fuelfit.exercise.api.model.ExerciseInfo
import com.example.fuelfit.exercise.impl.presentation.list.mvi.ExercisesIntent

@Composable
fun ExercisesScreen(component: ExercisesComponent) {
    val state by component.state.subscribeAsState()
    val snackbarHost = remember { SnackbarHostState() }
    var isFilterOpen by remember { mutableStateOf(false) }

    LaunchedEffect(state.error) {
        state.error?.let { snackbarHost.showSnackbar(it) }
    }

    LaunchedEffect(isFilterOpen) {
        if (isFilterOpen && state.categories.isEmpty())
            component.onIntent(ExercisesIntent.LoadCategories)
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = state.query,
                onValueChange = { component.onIntent(ExercisesIntent.SearchQueryChanged(it)) },
                label = { Text("Поиск") },
                modifier = Modifier.weight(1f)
            )


            Spacer(modifier = Modifier.width(8.dp))


            Button(onClick = { isFilterOpen = true }) {
                Text("Фильтр")
            }
        }


        Spacer(modifier = Modifier.height(16.dp))


        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        } else {
            val filtered = state.exercises?.exercises
                ?.filter {
                    it.translations.firstOrNull()?.name
                        ?.contains(state.query, ignoreCase = true) == true
                }.orEmpty()

            LazyColumn {
                items(filtered) { ExerciseItem(it) }
            }
        }
    }


    // ---------- FILTER DIALOG ----------
    if (isFilterOpen) {
        AlertDialog(
            onDismissRequest = { isFilterOpen = false },
            title = { Text("Фильтры") },
            text = {
                Column {

                    Text("Категории")
                    Spacer(Modifier.height(8.dp))

                    state.categories.forEach { cat ->
                        val checked = cat.id in state.selectedCategories
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(cat.name)
                            Switch(
                                checked = checked,
                                onCheckedChange = {
                                    component.onIntent(
                                        ExercisesIntent.CategoryToggled(
                                            categoryId = cat.id,
                                            isChecked = it
                                        )
                                    )
                                }
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { isFilterOpen = false }) {
                    Text("Готово")
                }
            }
        )
    }

    SnackbarHost(snackbarHost)
}

@Composable
fun ExerciseItem(ex: ExerciseInfo) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(ex.translations.firstOrNull()?.name ?: "No name")
            Spacer(Modifier.height(4.dp))
            Text(ex.musclesLine())
        }
    }
}