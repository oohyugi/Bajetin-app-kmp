package com.bajetin.app.features.main.presentation

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.bajetin.app.core.utils.ScreenSize
import com.bajetin.app.navigation.BottomNavItem
import com.bajetin.app.navigation.NavigationHost
import com.bajetin.app.ui.component.BottomNavBar
import com.bajetin.app.ui.component.NavRailBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navHostController = rememberNavController()
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    )
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

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Clock.System.now().toEpochMilliseconds()
    )
    val datePickerSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showDatePickerSheet by remember { mutableStateOf(false) }
    var selectedDate: Long? = null

    // close date picker
    LaunchedEffect(datePickerState.selectedDateMillis) {
        if (selectedDate != datePickerState.selectedDateMillis && showDatePickerSheet) {
            showDatePickerSheet = false
            addTransactionViewModel.onSelectedDate(datePickerState.selectedDateMillis)
            datePickerSheetState.hide()
        }
        selectedDate = datePickerState.selectedDateMillis
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContainerColor = MaterialTheme.colorScheme.surface,
        sheetShadowElevation = 4.dp,
        sheetContent = {
            AddTransactionSheet(
                modifier = Modifier.fillMaxWidth(),
                viewModel = addTransactionViewModel,
                onEventLaunch = { event ->
                    when (event) {
                        AddTransactionUiEvent.HideSheet -> {
                            scope.launch {
                                scaffoldState.bottomSheetState.hide()
                            }
                        }

                        AddTransactionUiEvent.ShowDatePicker -> {
                            showDatePickerSheet = true
                            scope.launch { datePickerSheetState.expand() }
                        }

                        is AddTransactionUiEvent.ShowSnackbar -> {
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(event.message)
                            }
                        }
                    }
                },
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

                // Bottom sheet date picker
                if (showDatePickerSheet) {
                    ModalBottomSheet(
                        sheetState = datePickerSheetState,
                        onDismissRequest = {
                            showDatePickerSheet = false
                            scope.launch { datePickerSheetState.hide() }
                        }
                    ) {
                        DatePicker(
                            state = datePickerState,
                            colors = DatePickerDefaults.colors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                            ),
                        )
                    }
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
