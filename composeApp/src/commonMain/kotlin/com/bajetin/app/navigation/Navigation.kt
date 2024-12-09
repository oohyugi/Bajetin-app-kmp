package com.bajetin.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bajetin.app.features.transaction.presentation.TransactionScreen

@Composable
fun Navigation(navHostController: NavHostController) {

    NavHost(
        navController = navHostController,
        startDestination = NavigationItem.Transaction.route
    ) {
        composable(route = NavigationItem.Transaction.route) {
            TransactionScreen()
        }
    }
}
