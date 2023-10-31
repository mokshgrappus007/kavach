package com.grappus.kavach.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

object KavachTheme {
    val lightColorScheme = lightColorScheme(
        primary = KavachColor.Purple40,
        secondary = KavachColor.PurpleGrey40,
        tertiary = KavachColor.Pink40,
        /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
    )

    val darkColorScheme = darkColorScheme(
        primary = KavachColor.Purple40,
        secondary = KavachColor.PurpleGrey40,
        tertiary = KavachColor.Pink40,
        background = KavachColor.RaisinBlack,
        /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
    )
}

@Composable
fun KavachTheme(
    colorScheme: ColorScheme,
    isDark: Boolean,
    content: @Composable () -> Unit
) {
    val colorScheme = colorScheme
    StatusBarTheme(isDark = isDark, background = colorScheme.background)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun StatusBarTheme(isDark: Boolean, background: Color) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars != isDark
        }
    }
}