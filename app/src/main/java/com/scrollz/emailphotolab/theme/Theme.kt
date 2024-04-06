package com.scrollz.emailphotolab.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

private val DarkColorScheme = darkColorScheme(
    primary = DarkBluePrimary,
    onPrimary = DarkGrayBackground,
    inversePrimary = DarkBlueInversePrimary,

    background = DarkGrayBackground,
    onBackground = DarkWhiteOnBackground,

    secondary = DarkGraySecondary,
    tertiary = DarkGrayTertiary,

    surface = DarkGraySurface,
    onSurface = DarkGrayOnSurface,
    onSurfaceVariant = DarkGrayOnSurfaceVariant,

    error = DarkRedError
)

private val LightColorScheme = lightColorScheme(
    primary = LightBluePrimary,
    onPrimary = LightWhiteBackground,
    inversePrimary = LightBlueInversePrimary,

    background = LightWhiteBackground,
    onBackground = LightBlackOnBackground,

    secondary = LightGraySecondary,
    tertiary = LightGrayTertiary,

    surface = LightGraySurface,
    onSurface = LightBlackOnSurface,
    onSurfaceVariant = LightGrayOnSurfaceVariant,

    error = LightRedError
)

@Composable
fun EmailPhotoLabTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window.apply {
                statusBarColor = colorScheme.background.toArgb()
                navigationBarColor = colorScheme.background.toArgb()
            }
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !darkTheme
                isAppearanceLightNavigationBars = !darkTheme
                hide(WindowInsetsCompat.Type.systemBars())
                systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun PhotoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()
            window.navigationBarColor = Color.Transparent.toArgb()
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = false
                isAppearanceLightNavigationBars = false
                hide(WindowInsetsCompat.Type.systemBars())
                systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
