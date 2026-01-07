package com.example.fuelfit.designsystem.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.fuelfit.designsystem.item.BottomNavItem

@Composable
fun <T> FuelFitBottomNavigation(
    items: List<BottomNavItem<T>>,
    currentId: T,
    onItemSelected: (T) -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    NavigationBar(
        modifier = modifier,
        containerColor = backgroundColor
    ) {
        items.forEach { item ->
            val isSelected = item.id == currentId
            NavigationBarItem(
                selected = isSelected,
                onClick = { onItemSelected(item.id) },
                label = { Text(item.label, color = if (isSelected) selectedColor else unselectedColor) },
                icon = {
                    Icon(
                        item.icon,
                        contentDescription = item.label,
                        tint = if (isSelected) selectedColor else unselectedColor
                    )
                },
                alwaysShowLabel = true
            )
        }
    }
}
