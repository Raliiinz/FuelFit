package com.example.fuelfit.exercise.impl.presentation.list.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.fuelfit.designsystem.components.*
import com.example.fuelfit.exercise.api.model.ExerciseCategory
import com.example.fuelfit.exercise.impl.R

@Composable
internal fun ExercisesFilterDialog(
    categories: List<ExerciseCategory>,
    selectedCategories: Set<Int>,
    onToggle: (Int, Boolean) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    FuelFitFilterDialog(
        title = stringResource(R.string.filters_title),
        confirmText = stringResource(R.string.filters_done),
        dismissText = stringResource(R.string.filters_cancel),
        onDismiss = onDismiss,
        onConfirm = onConfirm
    ) {
        FuelFitText.TitleSmall(
            text = stringResource(R.string.filters_categories)
        )

        categories.forEach { category ->
            FuelFitFilterItem(
                title = category.name,
                checked = category.id in selectedCategories,
                onCheckedChange = { checked ->
                    onToggle(category.id, checked)
                }
            )
        }
    }
}
