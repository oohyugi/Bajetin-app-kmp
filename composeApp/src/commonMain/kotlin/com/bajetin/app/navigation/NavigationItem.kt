package com.bajetin.app.navigation

import bajetin.composeapp.generated.resources.Res
import bajetin.composeapp.generated.resources.ic_add
import bajetin.composeapp.generated.resources.ic_graph
import bajetin.composeapp.generated.resources.ic_transaction
import org.jetbrains.compose.resources.DrawableResource

sealed class NavigationItem(
    val route: String,
    val title: String,
    val icon: DrawableResource? = null
) {

    data object Transaction :
        NavigationItem(route = "/transaction", "Transaction", Res.drawable.ic_transaction)

    data object Add : NavigationItem(route = "", "", Res.drawable.ic_add)
    data object Report : NavigationItem(route = "/report", "Report", Res.drawable.ic_graph)
}
