package com.example.fuelfit.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

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
    primary = Color(0xFFFF8C1A),
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF5A2A00),
    onPrimaryContainer = Color(0xFFFFE1C4),

    secondary = Color(0xFFB89B8C),
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF3B2A25),
    onSecondaryContainer = Color(0xFFD7C2B5),

    tertiary = Color(0xFFFFCC80),
    onTertiary = Color.Black,
    tertiaryContainer = Color(0xFF5A3A10),
    onTertiaryContainer = Color(0xFFFFE8CC),

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
