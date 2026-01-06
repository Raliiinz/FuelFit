package com.example.fuelfit.designsystem.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Shape

object FuelFitCard {
    @Composable
    fun FuelFitBaseCard(
        modifier: Modifier = Modifier,
        shape: RoundedCornerShape = RoundedCornerShape(12.dp),
        backgroundColor: Color = MaterialTheme.colorScheme.surface,
        contentColor: Color = MaterialTheme.colorScheme.onSurface,
        content: @Composable () -> Unit
    ) {
        Card(
            modifier = modifier,
            shape = shape,
            colors = CardDefaults.cardColors(
                containerColor = backgroundColor,
                contentColor = contentColor
            )
        ) {
            Box(modifier = Modifier.padding(12.dp)) {
                content()
            }
        }
    }

    @Composable
    fun FuelFitOutlinedCard(
        modifier: Modifier = Modifier,
        shape: Shape = RoundedCornerShape(12.dp),
        borderColor: Color = MaterialTheme.colorScheme.primary,
        borderWidth: Float = 1f,
        contentPadding: PaddingValues = PaddingValues(12.dp),
        content: @Composable () -> Unit
    ) {
        Box(
            modifier = modifier
                .border(width = borderWidth.dp, color = borderColor, shape = shape)
                .padding(contentPadding)
        ) {
            content()
        }
    }
}
