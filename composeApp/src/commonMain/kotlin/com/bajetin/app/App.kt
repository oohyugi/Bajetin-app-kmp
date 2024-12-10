package com.bajetin.app

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bajetin.app.core.ui.component.BottomNavBar
import com.bajetin.app.core.ui.component.NavRailBar
import com.bajetin.app.core.ui.theme.BajetinTheme
import com.bajetin.app.core.viewmodel.TransactionViewModel
import com.bajetin.app.di.platformModule
import com.bajetin.app.di.viewModelModule
import com.bajetin.app.features.transaction.presentation.AddTransactionSheet
import com.bajetin.app.navigation.Navigation
import com.bajetin.app.navigation.NavigationItem
import com.bajetin.app.core.utils.ScreenSize
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(ExperimentalMaterial3Api::class, KoinExperimentalAPI::class)
@Composable
fun App() {
    KoinApplication(
        application = {
            modules(platformModule, viewModelModule)
        }
    ) {
        BajetinTheme {
            val navHostController = rememberNavController()
            val scope = rememberCoroutineScope()
            val scaffoldState = rememberBottomSheetScaffoldState()
            var screenSize by remember { mutableStateOf(ScreenSize.COMPACT) }

            val topLevelDestinations = listOf(
                NavigationItem.Transaction,
                NavigationItem.Add,
                NavigationItem.Report,
            )
            val currentDestination =
                navHostController.currentBackStackEntryAsState().value?.destination?.route
            val isTopLevelDestination =
                currentDestination in topLevelDestinations.map { it.route }

            val showNavigationRail = screenSize != ScreenSize.COMPACT

            val transactionViewModel = koinViewModel<TransactionViewModel>()

            BottomSheetScaffold(
                scaffoldState = scaffoldState,
                sheetPeekHeight = 0.dp,
                sheetContainerColor = MaterialTheme.colorScheme.surface,
                sheetShadowElevation = 4.dp,
                sheetContent = {
                    AddTransactionSheet(
                        modifier = Modifier.fillMaxWidth(),
                        viewModel = transactionViewModel
                    )
                },
            ) {
                Scaffold(bottomBar = {
                    if (isTopLevelDestination && !showNavigationRail) {
                        BottomNavBar(
                            bottomNavItems = topLevelDestinations,
                            currentRoute = currentDestination,
                            onNavBarClick = { item ->
                                onNavBarClick(
                                    item,
                                    scope,
                                    scaffoldState,
                                    currentDestination,
                                    navHostController
                                )
                            }
                        )
                    }
                }) {
                    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                        screenSize = ScreenSize.basedOnWidth(this.maxWidth)

                        Row(modifier = Modifier.fillMaxSize()) {
                            if (isTopLevelDestination && showNavigationRail) {
                                NavRailBar(
                                    items = topLevelDestinations,
                                    currentRoute = currentDestination,
                                    onNavBarClick = {
                                        onNavBarClick(
                                            item = it,
                                            scope,
                                            scaffoldState,
                                            currentDestination,
                                            navHostController
                                        )
                                    }
                                )
                            }

                            Navigation(
                                navHostController = navHostController,
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
private fun onNavBarClick(
    item: NavigationItem,
    scope: CoroutineScope,
    scaffoldState: BottomSheetScaffoldState,
    currentDestination: String?,
    navHostController: NavHostController
) {
    when (item) {
        NavigationItem.Add -> {
            scope.launch {
                scaffoldState.bottomSheetState.expand()
            }
        }

        else -> {
            if (item.route != currentDestination) {
                navHostController.navigate(route = item.route)
            }
        }
    }
}
