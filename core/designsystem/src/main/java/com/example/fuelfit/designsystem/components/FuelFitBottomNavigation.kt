package com.example.fuelfit.designsystem.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.fuelfit.designsystem.theme.Primary
import com.example.fuelfit.designsystem.theme.OnPrimary

data class BottomNavItem<T>(
    val id: T,
    val label: String,
    val icon: ImageVector
)

@Composable
fun <T> FuelFitBottomNavigation(
    items: List<BottomNavItem<T>>,
    currentId: T,
    onItemSelected: (T) -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Primary,
    contentColor: Color = OnPrimary
) {
    NavigationBar(
        modifier = modifier,
        containerColor = backgroundColor
    ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = item.id == currentId,
                onClick = { onItemSelected(item.id) },
                label = { Text(item.label, color = contentColor) },
                icon = { Icon(item.icon, contentDescription = item.label, tint = contentColor) },
                alwaysShowLabel = true
            )
        }
    }
}
