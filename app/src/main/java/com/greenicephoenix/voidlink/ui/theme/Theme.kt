package com.greenicephoenix.voidlink.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Lock the color scheme to our dark Nothing aesthetic
private val VoidLinkColorScheme = darkColorScheme(
    primary = StarkWhite,
    onPrimary = PureBlack,
    background = PureBlack,
    surface = PureBlack, // Background of cards/dialogs
    onBackground = StarkWhite,
    onSurface = StarkWhite,
    error = SignatureRed,
    onError = StarkWhite,
    surfaceVariant = DarkGrey,
    onSurfaceVariant = LightGrey
)

@Composable
fun VoidLinkTheme(
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // Make the system status bar pure black to match the app
            window.statusBarColor = PureBlack.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = VoidLinkColorScheme,
        typography = Typography,
        content = content
    )
}