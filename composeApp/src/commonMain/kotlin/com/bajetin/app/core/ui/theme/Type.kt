package com.bajetin.app.core.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import bajetin.composeapp.generated.resources.Res
import bajetin.composeapp.generated.resources.raleway_regular
import org.jetbrains.compose.resources.Font
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import bajetin.composeapp.generated.resources.raleway_bold
import bajetin.composeapp.generated.resources.raleway_semibold

@Composable
fun RalewayTypography(): Typography {
    val raleway = FontFamily(
        Font(
            resource = Res.font.raleway_regular,
            weight = FontWeight.Normal,
            style = FontStyle.Normal
        ),

        Font(
            resource = Res.font.raleway_bold,
            weight = FontWeight.Bold,
            style = FontStyle.Normal
        ),
        Font(
            resource = Res.font.raleway_semibold,
            weight = FontWeight.SemiBold,
            style = FontStyle.Normal
        ),
    )

    return Typography(
        headlineSmall = TextStyle(
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            fontFamily = raleway
        ),
        titleLarge = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            fontFamily = raleway
        ),
        bodyLarge = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            fontFamily = raleway
        ),
        bodyMedium = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            fontFamily = raleway
        ),

        labelMedium = TextStyle(
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            fontFamily = raleway
        ),

        labelSmall = TextStyle(
            fontWeight = FontWeight.SemiBold,
            fontSize = 11.sp,
            fontFamily = raleway
        ),
    )
}
