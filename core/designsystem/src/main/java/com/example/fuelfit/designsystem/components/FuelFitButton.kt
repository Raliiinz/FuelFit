package com.example.fuelfit.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object FuelFitButton {

    @Composable
    fun Primary(
        text: String,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        contentPadding: PaddingValues = PaddingValues(
            horizontal = 20.dp,
            vertical = 12.dp
        ),
        minHeight: Dp = 48.dp
    ) {
        Button(
            onClick = onClick,
            modifier = modifier.defaultMinSize(minHeight = minHeight),
            enabled = enabled,
            contentPadding = contentPadding,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            FuelFitText.LabelLarge(text)
        }
    }

    @Composable
    fun Secondary(
        text: String,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        contentPadding: PaddingValues = PaddingValues(
            horizontal = 20.dp,
            vertical = 12.dp
        ),
        minHeight: Dp = 48.dp
    ) {
        Button(
            onClick = onClick,
            modifier = modifier.defaultMinSize(minHeight = minHeight),
            enabled = enabled,
            contentPadding = contentPadding,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            )
        ) {
            FuelFitText.LabelLarge(text)
        }
    }

    @Composable
    fun Outlined(
        text: String,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        contentPadding: PaddingValues = PaddingValues(
            horizontal = 20.dp,
            vertical = 12.dp
        ),
        minHeight: Dp = 48.dp
    ) {
        OutlinedButton(
            onClick = onClick,
            modifier = modifier.defaultMinSize(minHeight = minHeight),
            enabled = enabled,
            contentPadding = contentPadding,
            border = BorderStroke(
                1.dp,
                MaterialTheme.colorScheme.outline
            )
        ) {
            FuelFitText.LabelLarge(text)
        }
    }

    @Composable
    fun TextUnderlined(
        text: String,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        enabled: Boolean = true
    ) {
        TextButton(
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
            colors = ButtonDefaults.textButtonColors(
                contentColor = MaterialTheme.colorScheme.primary
            ),
            contentPadding = PaddingValues(0.dp)
        ) {
            FuelFitText.BodyMedium(
                text = text,
                modifier = Modifier,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
