package com.bajetin.app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(
    val route: String,
    val title: String,
    val icon: ImageVector? = null
) {

    data object Transaction :
        NavigationItem(route = "/transaction", "Transaction", Icons.Rounded.PlayArrow)

    data object Report : NavigationItem(route = "/report", "Report", Icons.Rounded.Call)
}
