package com.bajetin.app.core.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

@Composable
fun BajetinTheme(
    colors: BajetinColors = BajetinTheme.colors,
    typography: BajetinTypography = BajetinTheme.typography,
    content: @Composable () -> Unit,
) {

    val rememberedColors = remember { colors.copy() }.apply { updateColors(colors) }
    CompositionLocalProvider(
        LocalColors provides rememberedColors,
        LocalTypography provides typography
    ) {
        content()
    }
}


object BajetinTheme {
    val colors: BajetinColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: BajetinTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current
}







