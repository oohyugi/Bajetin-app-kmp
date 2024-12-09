package com.bajetin.app.utils

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class ScreenSize {
    COMPACT,
    MEDIUM,
    EXPANDED;

    companion object {
        fun basedOnWidth(width: Dp): ScreenSize {
            return when {
                width < 600.dp -> COMPACT
                width < 840.dp -> MEDIUM
                else -> EXPANDED
            }
        }
    }
}
