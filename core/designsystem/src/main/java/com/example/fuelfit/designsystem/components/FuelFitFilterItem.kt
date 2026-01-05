package com.example.fuelfit.designsystem.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun FuelFitFilterItem(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FuelFitText.BodyLarge(
            text = title,
            modifier = Modifier.weight(1f)
        )

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}
