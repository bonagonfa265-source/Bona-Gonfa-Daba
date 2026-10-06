package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val BonaDarkColorScheme = darkColorScheme(
    primary = DiamondCyan,
    onPrimary = DarkObsidian,
    primaryContainer = CyanContainer,
    onPrimaryContainer = OnCyanContainer,
    secondary = ElectricViolet,
    onSecondary = DarkObsidian,
    tertiary = RadiantGold,
    onTertiary = DarkObsidian,
    background = DarkObsidian,
    onBackground = TextPrimary,
    surface = DarkSurface,
    onSurface = TextPrimary,
    surfaceVariant = DarkSurfaceElevated,
    onSurfaceVariant = TextSecondary,
    outline = DarkCardBorder,
    outlineVariant = DarkCardBorder
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true, // Default to Bona Graphics signature dark aesthetic
    content: @Composable () -> Unit
) {
    val colorScheme = BonaDarkColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = DarkObsidian.toArgb()
                window.navigationBarColor = DarkObsidian.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
                WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = false
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
