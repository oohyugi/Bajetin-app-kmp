package com.bajetin.app.features.main.presentation

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
import com.bajetin.app.ui.component.BottomNavBar
import com.bajetin.app.ui.component.NavRailBar
import com.bajetin.app.core.utils.ScreenSize
import com.bajetin.app.navigation.BottomNavItem
import com.bajetin.app.navigation.NavigationHost
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(ExperimentalMaterial3Api::class, KoinExperimentalAPI::class)
@Composable
fun MainScreen() {
    val navHostController = rememberNavController()
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    var screenSize by remember { mutableStateOf(ScreenSize.COMPACT) }

    val topLevelDestinations = listOf(
        BottomNavItem.Transaction,
        BottomNavItem.Add,
        BottomNavItem.Report,
    )
    val currentDestination =
        navHostController.currentBackStackEntryAsState().value?.destination?.route
    val isTopLevelDestination =
        currentDestination in topLevelDestinations.map { it.route }

    val showNavigationRail = screenSize != ScreenSize.COMPACT

    val addTransactionViewModel = koinViewModel<AddTransactionViewModel>()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContainerColor = MaterialTheme.colorScheme.surface,
        sheetShadowElevation = 4.dp,
        sheetContent = {
            AddTransactionSheet(
                modifier = Modifier.fillMaxWidth(),
                viewModel = addTransactionViewModel
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

                    NavigationHost(
                        navHostController = navHostController,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
private fun onNavBarClick(
    item: BottomNavItem,
    scope: CoroutineScope,
    scaffoldState: BottomSheetScaffoldState,
    currentDestination: String?,
    navHostController: NavHostController
) {
    when (item) {
        BottomNavItem.Add -> {
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
