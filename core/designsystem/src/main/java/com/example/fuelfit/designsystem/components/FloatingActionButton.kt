package com.example.fuelfit.designsystem.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

object FuelFitFab {

    @Composable
    fun FloatingActionButton(
        onClick: () -> Unit,
        contentDescription: String,
        modifier: Modifier = Modifier,
        containerColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.secondaryContainer,
        contentColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.onSecondaryContainer,
        icon: @Composable () -> Unit
    ) {
        FloatingActionButton(
            onClick = onClick,
            containerColor = containerColor,
            contentColor = contentColor,
            modifier = modifier.padding(24.dp)
        ) {
            icon()
        }
    }
}
