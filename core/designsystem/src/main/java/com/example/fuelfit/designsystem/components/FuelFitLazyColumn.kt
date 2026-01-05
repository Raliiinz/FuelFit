package com.example.fuelfit.designsystem.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FuelFitLazyColumn(
    modifier: Modifier = Modifier,
    state: LazyListState,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = 8.dp,
        vertical = 4.dp
    ),
    content: LazyListScope.() -> Unit
) {
    LazyColumn(
        modifier = modifier,
        state = state,
        contentPadding = contentPadding
    ) {
        content()
    }
}
