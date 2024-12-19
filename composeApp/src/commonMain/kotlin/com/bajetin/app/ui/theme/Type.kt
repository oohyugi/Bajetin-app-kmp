package com.bajetin.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import bajetin.composeapp.generated.resources.Res
import bajetin.composeapp.generated.resources.nunito_bold
import bajetin.composeapp.generated.resources.nunito_semi_bold
import bajetin.composeapp.generated.resources.nutino_regular
import org.jetbrains.compose.resources.Font

@Composable
fun NutinoTypography(): Typography {
    val nutino = FontFamily(
        Font(
            resource = Res.font.nutino_regular,
            weight = FontWeight.Normal,
            style = FontStyle.Normal
        ),

        Font(
            resource = Res.font.nunito_bold,
            weight = FontWeight.Bold,
            style = FontStyle.Normal
        ),
        Font(
            resource = Res.font.nunito_semi_bold,
            weight = FontWeight.SemiBold,
            style = FontStyle.Normal
        ),
    )

    return Typography(
        headlineSmall = TextStyle(
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            fontFamily = nutino
        ),
        headlineMedium = TextStyle(
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            fontFamily = nutino
        ),
        titleLarge = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            fontFamily = nutino
        ),
        titleMedium = TextStyle(
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            fontFamily = nutino
        ),
        bodyLarge = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            fontFamily = nutino
        ),
        bodyMedium = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            fontFamily = nutino
        ),

        labelMedium = TextStyle(
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            fontFamily = nutino
        ),

        labelSmall = TextStyle(
            fontWeight = FontWeight.SemiBold,
            fontSize = 11.sp,
            fontFamily = nutino
        ),
        labelLarge = TextStyle(
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            fontFamily = nutino
        ),
    )
}
