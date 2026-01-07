package com.example.fuelfit.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,

    secondary = Secondary,
    onSecondary = OnSecondary,
    secondaryContainer = SecondaryContainer,
    onSecondaryContainer = OnSecondaryContainer,

    tertiary = Tertiary,
    onTertiary = OnTertiary,
    tertiaryContainer = TertiaryContainer,
    onTertiaryContainer = OnTertiaryContainer,

    background = Background,
    onBackground = OnBackground,
    surface = Surface,
    surfaceVariant = SurfaceVariant,
    onSurface = OnBackground,

    outline = Outline,
    error = Error,
    onError = OnError
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryContainer,
    onPrimary = Color.Black,
    primaryContainer = Primary,
    onPrimaryContainer = OnPrimary,

    secondary = SecondaryContainer,
    onSecondary = Color.Black,
    secondaryContainer = Secondary,
    onSecondaryContainer = OnSecondary,

    tertiary = TertiaryContainer,
    onTertiary = Color.Black,
    tertiaryContainer = Tertiary,
    onTertiaryContainer = OnTertiary,

    background = DarkBackground,
    onBackground = Color(0xFFF5EFEA),
    surface = DarkSurface,
    onSurface = Color(0xFFF5EFEA),

    outline = DarkOutline,
    error = Error,
    onError = Color.Black
)

@Composable
fun FuelFitTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
