package com.example.fuelfit.exercise.impl.presentation.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.fuelfit.designsystem.ErrorContent
import com.example.fuelfit.designsystem.LoadingContent
import com.example.fuelfit.exercise.impl.presentation.list.components.ExercisesFilterDialog
import com.example.fuelfit.exercise.impl.presentation.list.components.ExercisesList
import com.example.fuelfit.exercise.impl.presentation.list.components.SearchAndFilterBar
import com.example.fuelfit.exercise.impl.presentation.list.mvi.ExercisesIntent
import com.example.fuelfit.utils.flow.LaunchedEffectAndCollect

@Composable
internal fun ExercisesListScreen(component: ExercisesListComponent) {
    val state by component.state.subscribeAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var isFilterOpen by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()

    LaunchedEffectAndCollect(
        flow = component.snackbarFlow,
        lifecycle = component.lifecycle
    ) { msg ->
        snackbarHostState.showSnackbar(msg)
    }

    // Загружаем категории при открытии фильтра
    LaunchedEffect(isFilterOpen) {
        if (isFilterOpen && state.categories.isEmpty()) {
            component.onIntent(ExercisesIntent.LoadCategories)
        }
    }

    // Автоподгрузка
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisible ->
                val total = listState.layoutInfo.totalItemsCount
                if (lastVisible != null && lastVisible >= total - 3 && !state.isPaging) {
                    component.onIntent(ExercisesIntent.LoadNextPage)
                }
            }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        SearchAndFilterBar(
            query = state.query,
            onQueryChange = { component.onIntent(ExercisesIntent.SearchQueryChanged(it)) },
            onFilterClick = { isFilterOpen = true }
        )

        Spacer(modifier = Modifier.height(16.dp))

        when {
            state.isLoading -> LoadingContent()
            state.error != null -> ErrorContent(
                state.error!!,
                onRetry = {
                    component.onIntent(ExercisesIntent.Refresh)
                }
            )
            else -> ExercisesList(
                exercises = state.exercises?.exercises.orEmpty(),
                query = state.query,
                listState = listState,
                onExerciseClick = { id ->
                    component.onIntent(ExercisesIntent.ExerciseClicked(id))
                }
            )
        }
    }

    if (isFilterOpen) {
        ExercisesFilterDialog(
            categories = state.categories,
            selectedCategories = state.selectedCategories,
            onToggle = { id, checked ->
                component.onIntent(ExercisesIntent.CategoryToggled(id, checked))
            },
            onConfirm = {
                component.onIntent(ExercisesIntent.Refresh)
                isFilterOpen = false
            },
            onDismiss = { isFilterOpen = false }
        )
    }

    SnackbarHost(snackbarHostState)
}
