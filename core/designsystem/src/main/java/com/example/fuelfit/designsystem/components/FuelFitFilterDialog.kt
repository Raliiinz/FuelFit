package com.example.fuelfit.designsystem.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun FuelFitFilterDialog(
    title: String,
    confirmText: String,
    dismissText: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    confirmEnabled: Boolean = true,
    content: @Composable ColumnScope.() -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            FuelFitText.TitleLarge(text = title)
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                content = content
            )
        },
        confirmButton = {
            FuelFitButton.Primary(
                text = confirmText,
                onClick = onConfirm,
                enabled = confirmEnabled
            )
        },
        dismissButton = {
            FuelFitButton.TextUnderlined(
                text = dismissText,
                onClick = onDismiss
            )
        }
    )
}
