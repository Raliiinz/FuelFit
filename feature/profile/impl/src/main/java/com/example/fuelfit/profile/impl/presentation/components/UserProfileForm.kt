package com.example.fuelfit.profile.impl.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.fuelfit.designsystem.components.FuelFitButton
import com.example.fuelfit.designsystem.components.FuelFitCard
import com.example.fuelfit.designsystem.components.FuelFitTextField
import com.example.fuelfit.profile.impl.R

@Composable
internal fun UserProfileForm(
    weight: String,
    onWeightChange: (String) -> Unit,
    height: String,
    onHeightChange: (String) -> Unit,
    age: String,
    onAgeChange: (String) -> Unit,
    onSave: () -> Unit,
    isSaving: Boolean,
    heightError: Boolean = false,
    ageError: Boolean = false,
) {
    FuelFitCard.FuelFitOutlinedCard(shape = RoundedCornerShape(16.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            FuelFitTextField.Outlined(
                value = weight,
                onValueChange = onWeightChange,
                label = stringResource(R.string.weight_label),
                modifier = Modifier.fillMaxWidth()
            )

            FuelFitTextField.Outlined(
                value = height,
                onValueChange = onHeightChange,
                label = stringResource(R.string.height_label),
                isError = heightError,
                errorMessage = if (heightError) stringResource(R.string.error_height_min) else null,
                modifier = Modifier.fillMaxWidth()
            )

            FuelFitTextField.Outlined(
                value = age,
                onValueChange = onAgeChange,
                label = stringResource(R.string.age_label),
                isError = ageError,
                errorMessage = if (ageError) stringResource(R.string.error_age_min) else null,
                modifier = Modifier.fillMaxWidth()
            )

            FuelFitButton.Primary(
                text = if (isSaving) stringResource(R.string.saving_text) else stringResource(R.string.save_button),
                onClick = onSave,
                modifier = Modifier.fillMaxWidth(),
                enabled = !isSaving
            )
        }
    }
}
