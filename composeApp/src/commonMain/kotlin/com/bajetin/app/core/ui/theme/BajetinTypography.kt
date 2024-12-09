package com.bajetin.app.core.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import com.bajetin.app.core.ui.theme.tokens.TypographyTokens

data class BajetinTypography internal constructor(
    val displayLarge: TextStyle,
    val displayMedium: TextStyle,
    val displaySmall: TextStyle,
    val eyebrowLarge: TextStyle,
    val eyebrowMedium: TextStyle,
    val eyebrowSmall: TextStyle,
    val headingLarge: TextStyle,
    val headingMedium: TextStyle,
    val headingSmall: TextStyle,
    val subheading: TextStyle,
    val action: TextStyle,
    val body: TextStyle,
    val bodySmall: TextStyle,
    val bodySmallBold: TextStyle,
    val helper: TextStyle,
    val helperBold: TextStyle,
    val helperSmall: TextStyle,
    val helperSmallBold: TextStyle
) {

    constructor(
        defaultFontFamily: FontFamily = FontFamily.Default,
        displayLarge: TextStyle = TypographyTokens.DisplayLarge,
        displayMedium: TextStyle = TypographyTokens.DisplayMedium,
        displaySmall: TextStyle = TypographyTokens.DisplaySmall,
        eyebrowLarge: TextStyle = TypographyTokens.EyebrowLarge,
        eyebrowMedium: TextStyle = TypographyTokens.EyebrowMedium,
        eyebrowSmall: TextStyle = TypographyTokens.EyebrowSmall,
        headingLarge: TextStyle = TypographyTokens.HeadingLarge,
        headingMedium: TextStyle = TypographyTokens.HeadingMedium,
        headingSmall: TextStyle = TypographyTokens.HeadingSmall,
        subheading: TextStyle = TypographyTokens.Subheading,
        action: TextStyle = TypographyTokens.Action,
        body: TextStyle = TypographyTokens.Body,
        bodySmall: TextStyle = TypographyTokens.BodySmall,
        bodySmallBold: TextStyle = TypographyTokens.BodySmallBold,
        helper: TextStyle = TypographyTokens.Helper,
        helperBold: TextStyle = TypographyTokens.HelperBold,
        helperSmall: TextStyle = TypographyTokens.HelperSmall,
        helperSmallBold: TextStyle = TypographyTokens.HelperSmallBold,
    ) : this(
        displayLarge = displayLarge.withDefaultFontFamily(defaultFontFamily),
        displayMedium = displayMedium.withDefaultFontFamily(defaultFontFamily),
        displaySmall = displaySmall.withDefaultFontFamily(defaultFontFamily),
        eyebrowLarge = eyebrowLarge.withDefaultFontFamily(defaultFontFamily),
        eyebrowMedium = eyebrowMedium.withDefaultFontFamily(defaultFontFamily),
        eyebrowSmall = eyebrowSmall.withDefaultFontFamily(defaultFontFamily),
        headingLarge = headingLarge.withDefaultFontFamily(defaultFontFamily),
        headingMedium = headingMedium.withDefaultFontFamily(defaultFontFamily),
        headingSmall = headingSmall.withDefaultFontFamily(defaultFontFamily),
        subheading = subheading.withDefaultFontFamily(defaultFontFamily),
        action = action.withDefaultFontFamily(defaultFontFamily),
        body = body.withDefaultFontFamily(defaultFontFamily),
        bodySmall = bodySmall.withDefaultFontFamily(defaultFontFamily),
        bodySmallBold = bodySmallBold.withDefaultFontFamily(defaultFontFamily),
        helper = helper.withDefaultFontFamily(defaultFontFamily),
        helperBold = helperBold.withDefaultFontFamily(defaultFontFamily),
        helperSmall = helperSmall.withDefaultFontFamily(defaultFontFamily),
        helperSmallBold = helperSmallBold.withDefaultFontFamily(defaultFontFamily)
    )
}


private fun TextStyle.withDefaultFontFamily(defaultFontFamily: FontFamily): TextStyle {
    return if (fontFamily != null) this else copy(fontFamily = defaultFontFamily)
}

fun bajetinTypography() = BajetinTypography()

internal val LocalTypography = staticCompositionLocalOf { bajetinTypography() }



