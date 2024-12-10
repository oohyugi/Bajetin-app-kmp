package com.bajetin.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bajetin.app.features.transactionHistory.presentation.TransactionHistoryScreen

@Composable
fun NavigationHost(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = BottomNavItem.Transaction.route
    ) {
        composable(route = BottomNavItem.Transaction.route) {
            TransactionHistoryScreen()
        }
    }
}
