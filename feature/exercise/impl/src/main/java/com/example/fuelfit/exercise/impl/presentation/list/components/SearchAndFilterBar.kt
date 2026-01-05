package com.example.fuelfit.exercise.impl.presentation.list.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.fuelfit.designsystem.components.FuelFitSearchBar
import com.example.fuelfit.exercise.impl.R

@Composable
internal fun SearchAndFilterBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onFilterClick: () -> Unit
) {
    FuelFitSearchBar(
        query = query,
        onQueryChange = onQueryChange,
        trailingIcon = {
            IconButton(onClick = onFilterClick) {
                Icon(
                    imageVector = Icons.Default.Tune,
                    contentDescription = stringResource(R.string.exercises_filter)
                )
            }
        },
        placeholder = stringResource(R.string.exercises_search_hint),
    )
}
