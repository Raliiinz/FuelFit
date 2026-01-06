package com.example.fuelfit.routine.impl.dayDetail.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.fuelfit.designsystem.LoadingContent
import com.example.fuelfit.designsystem.components.FuelFitLazyColumn
import com.example.fuelfit.designsystem.components.FuelFitListCard
import com.example.fuelfit.designsystem.components.FuelFitSearchBar
import com.example.fuelfit.designsystem.components.FuelFitText
import com.example.fuelfit.exercise.api.model.ExerciseSearchItem
import com.example.fuelfit.routine.impl.R

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

        FuelFitText.HeadlineMedium(
            text = stringResource(R.string.exercise_search_title)
        )

        Spacer(modifier = Modifier.height(12.dp))

        FuelFitSearchBar(
            query = query,
            onQueryChange = onQueryChange,
            placeholder = stringResource(R.string.exercise_search_placeholder)
        )

        Spacer(modifier = Modifier.height(12.dp))

        when {
            isSearching -> {
                LoadingContent()
            }

            results.isEmpty() && query.isNotBlank() -> {
                FuelFitText.BodyMedium(
                    text = stringResource(R.string.exercise_search_empty)
                )
            }

            results.isNotEmpty() -> {
                val listState = rememberLazyListState()

                FuelFitLazyColumn(
                    modifier = Modifier.fillMaxHeight(),
                    state = listState
                ) {
                    items(
                        items = results.withIndex().toList(),
                        key = { "${it.index}_${it.value.id}_${it.value.baseId}" }
                    ) { indexedExercise ->
                        val exercise = indexedExercise.value

                        FuelFitListCard(
                            onClick = { onExerciseClick(exercise) }
                        ) {
                            FuelFitText.BodyLarge(
                                text = exercise.name
                            )
                            FuelFitText.BodySmall(
                                text = exercise.category
                            )
                        }
                    }
                }
            }
        }
    }
}
