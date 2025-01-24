package com.bajetin.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bajetin.app.features.transaction.presentation.TransactionHistoryScreen
import com.bajetin.app.features.transaction.presentation.TransactionViewModel
import org.koin.compose.koinInject

@Composable
fun NavigationHost(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = BottomNavItem.Transaction.route
    ) {
        composable(route = BottomNavItem.Transaction.route) {
            val viewModel: TransactionViewModel = koinInject()
            TransactionHistoryScreen(viewModel, onItemClick = {
                navHostController.currentBackStackEntry?.savedStateHandle?.set(
                    SelectedTransactionKey,
                    it
                )
            })
        }
    }
}

const val SelectedTransactionKey = "selectedTransaction"
