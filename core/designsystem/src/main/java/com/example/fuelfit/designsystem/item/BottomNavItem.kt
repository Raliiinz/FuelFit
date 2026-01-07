package com.example.fuelfit.designsystem.item

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem<T>(
    val id: T,
    val label: String,
    val icon: ImageVector
)
