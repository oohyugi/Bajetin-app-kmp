package com.bajetin.app.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val darkColorSchema = darkColorScheme(
    primary = DarkPrimaryColor,
    secondary = DarkPrimaryColor,
    surface = DarkSurface,
    onSurface = DarkTextPrimary,
    background = DarkSurface
)

private val lightColorSchema = lightColorScheme(
    primary = PrimaryColor,
    secondary = PrimaryColor,
    surface = Surface,
    onSurface = TextPrimary,
    background = Surface
)

@Composable
fun BajetinTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorSchema = if (darkTheme) {
        darkColorSchema
    } else {
        lightColorSchema
    }

    MaterialTheme(
        colorScheme = colorSchema,
        typography = RalewayTypography(),
        shapes = Shapes,
        content = content
    )
}
