package com.ianschoenrock.gnomelauncher.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary =  Color.Transparent,
    primaryVariant =  Color.Transparent,
    secondary =  Color.Transparent,
    onBackground = Color.Transparent,
)

private val LightColorPalette = lightColors(
    primary =  Color.Transparent,
    primaryVariant =  Color.Transparent,
    secondary =  Color.Transparent,
    onBackground = Color.Transparent,

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun GnomeLauncherTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}