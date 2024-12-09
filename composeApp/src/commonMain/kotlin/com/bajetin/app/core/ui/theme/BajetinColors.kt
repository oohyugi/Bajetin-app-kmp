package com.bajetin.app.core.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import com.bajetin.app.core.ui.theme.tokens.PaletteTokens

class BajetinColors(
    primary: Color,
    primary100: Color,
    primary200: Color,
    primary300: Color,
    primary400: Color,
    primary500: Color,
    primary600: Color,
    primary700: Color,
    primary800: Color,
    secondary: Color,
    secondary100: Color,
    secondary200: Color,
    secondary300: Color,
    backgroundDefault: Color,
    tertiary: Color,
    error: Color,
    disable: Color,
    success: Color,
    warning: Color,
    textRegularDark: Color,
    textSubduedDark: Color,
    textDisableDark: Color,
    textRegularLight: Color,
    textError: Color,

    ) {

    var primary by mutableStateOf(primary)
        private set
    var primary100 by mutableStateOf(primary100)
        private set
    var primary200 by mutableStateOf(primary200)
        private set
    var primary300 by mutableStateOf(primary300)
        private set
    var primary400 by mutableStateOf(primary400)
        private set
    var primary500 by mutableStateOf(primary500)
        private set
    var primary600 by mutableStateOf(primary600)
        private set
    var primary700 by mutableStateOf(primary700)
        private set
    var primary800 by mutableStateOf(primary800)
        private set

    var secondary by mutableStateOf(secondary)
        private set
    var secondary100 by mutableStateOf(secondary100)
        private set
    var secondary200 by mutableStateOf(secondary200)
        private set
    var secondary300 by mutableStateOf(secondary300)
        private set
    var backgroundDefault by mutableStateOf(backgroundDefault)
        private set

    var tertiary by mutableStateOf(tertiary)
        private set

    var error by mutableStateOf(error)
        private set

    var disable by mutableStateOf(disable)
        private set

    var success by mutableStateOf(success)
        private set

    var warning by mutableStateOf(warning)
        private set

    var textRegularDark by mutableStateOf(textRegularDark)
        private set

    var textSubduedDark by mutableStateOf(textSubduedDark)
        private set

    var textRegularLight by mutableStateOf(textRegularLight)
        private set

    var textDisableDark by mutableStateOf(textDisableDark)
        private set

    var textError by mutableStateOf(textError)
        private set


    fun copy(
        primary: Color = this.primary,
        primary100: Color = this.primary100,
        primary200: Color = this.primary200,
        primary300: Color = this.primary300,
        primary400: Color = this.primary400,
        primary500: Color = this.primary500,
        primary600: Color = this.primary600,
        primary700: Color = this.primary700,
        primary800: Color = this.primary800,
        secondary: Color  = this.secondary,
        secondary100: Color  = this.secondary100,
        secondary200: Color  = this.secondary200,
        secondary300: Color  = this.secondary300,
        backgroundDefault: Color = this.backgroundDefault,
        tertiary: Color = this.tertiary,
        error: Color = this.error,
        disable: Color = this.disable,
        success: Color = this.success,
        warning: Color = this.warning,
        textRegularDark: Color = this.textRegularDark,
        textSubduedDark: Color = this.textSubduedDark,
        textDisableDark: Color = this.textDisableDark,
        textRegularLight: Color = this.textRegularLight,
        textError: Color = this.textError,
    ): BajetinColors = BajetinColors(
        primary,
        primary100 = primary100,
        primary200 = primary200,
        primary300 = primary300,
        primary400 = primary400,
        primary500 = primary500,
        primary600 = primary600,
        primary700 = primary700,
        primary800 = primary800,
        secondary = secondary,
        secondary100 = secondary100,
        secondary200 = secondary200,
        secondary300 = secondary300,
        backgroundDefault = backgroundDefault,
        tertiary = tertiary,
        error = error,
        disable = disable,
        success = success,
        warning = warning,
        textRegularDark = textRegularDark,
        textSubduedDark = textSubduedDark,
        textDisableDark = textDisableDark,
        textRegularLight = textRegularLight,
        textError = textError
    )

    fun updateColors(colors: BajetinColors) {
        this.primary = colors.primary
        this.primary100 = colors.primary100
        this.primary200 = colors.primary200
        this.primary300 = colors.primary300
        this.primary400 = colors.primary400
        this.primary500 = colors.primary500
        this.primary600 = colors.primary600
        this.primary700 = colors.primary700
        this.primary800 = colors.primary800
        this.secondary = colors.secondary
        this.secondary100 = colors.secondary100
        this.secondary200 = colors.secondary200
        this.secondary300 = colors.secondary300
        this.backgroundDefault = colors.backgroundDefault
        this.tertiary = colors.tertiary
        this.error = colors.error
        this.disable = colors.disable
        this.success = colors.success
        this.warning = colors.warning
        this.textRegularDark = colors.textRegularDark
        this.textSubduedDark = colors.textSubduedDark
        this.textDisableDark = colors.textDisableDark
        this.textRegularLight = colors.textRegularLight
        this.textError = colors.textError
    }

    fun contentColorFor(backgroundColor: Color): Color {
        return when (backgroundColor) {
            primary, success -> textRegularLight
            backgroundDefault -> textRegularDark
            else -> Color.Unspecified
        }
    }
}

internal fun lightColors(
    primary: Color = PaletteTokens.primary,
    primary100: Color = PaletteTokens.primary100,
    primary200: Color = PaletteTokens.primary200,
    primary300: Color = PaletteTokens.primary300,
    primary400: Color = PaletteTokens.primary400,
    primary500: Color = PaletteTokens.primary500,
    primary600: Color = PaletteTokens.primary600,
    primary700: Color = PaletteTokens.primary700,
    primary800: Color = PaletteTokens.primary800,
    secondary: Color  = PaletteTokens.secondary,
    secondary100: Color  = PaletteTokens.secondary100,
    secondary200: Color  = PaletteTokens.secondary200,
    secondary300: Color  = PaletteTokens.secondary300,
    backgroundDefault: Color = PaletteTokens.White,
    tertiary: Color = PaletteTokens.Black,
    error: Color = PaletteTokens.Orange,
    disable: Color = PaletteTokens.LightGray,
    success: Color = Color.Green,
    warning: Color = Color.Yellow,
    textRegularDark: Color = PaletteTokens.Black,
    textSubduedDark: Color = PaletteTokens.Gray,
    textDisableDark: Color = PaletteTokens.LightGray,
    textRegularLight: Color = PaletteTokens.White,
    textError: Color = PaletteTokens.Orange,
): BajetinColors = BajetinColors(
    primary = primary,
    primary100 = primary100,
    primary200 = primary200,
    primary300 = primary300,
    primary400 = primary400,
    primary500 = primary500,
    primary600 = primary600,
    primary700 = primary700,
    primary800 = primary800,
    secondary = secondary,
    secondary100 = secondary100,
    secondary200 = secondary200,
    secondary300 = secondary300,
    backgroundDefault = backgroundDefault,
    tertiary = tertiary,
    error = error,
    disable = disable,
    success = success,
    warning = warning,
    textRegularDark = textRegularDark,
    textSubduedDark = textSubduedDark,
    textDisableDark = textDisableDark,
    textRegularLight = textRegularLight,
    textError = textError
)

@Composable
@ReadOnlyComposable
fun contentColorFor(backgroundColor: Color) =
    BajetinTheme.colors.contentColorFor(backgroundColor).takeOrElse { LocalContentColors.current }

internal val LocalColors = compositionLocalOf { lightColors() }
val LocalContentColors = compositionLocalOf { PaletteTokens.White }

