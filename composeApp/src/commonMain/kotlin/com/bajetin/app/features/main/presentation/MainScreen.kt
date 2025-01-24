package com.bajetin.app.features.main.presentation

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bajetin.app.core.utils.DateTimeUtils
import com.bajetin.app.core.utils.ScreenSize
import com.bajetin.app.core.utils.toLocalDate
import com.bajetin.app.features.main.presentation.addTransaction.AddTransactionSheet
import com.bajetin.app.features.main.presentation.addTransaction.AddTransactionUiEvent
import com.bajetin.app.features.main.presentation.category.CategorySheet
import com.bajetin.app.navigation.BottomNavItem
import com.bajetin.app.navigation.BottomNavItem.Companion.topLevelDestinations
import com.bajetin.app.navigation.NavigationHost
import com.bajetin.app.navigation.SelectedTransactionKey
import com.bajetin.app.ui.component.BottomNavBar
import com.bajetin.app.ui.component.NavRailBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
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

    val currentDestination =
        navHostController.currentBackStackEntryAsState().value?.destination?.route
    val isTopLevelDestination =
        currentDestination in topLevelDestinations.map { it.route }

    val showNavigationRail = screenSize != ScreenSize.COMPACT

    val addTransactionViewModel = koinViewModel<AddTransactionViewModel>()

    val addTransactionUiState =
        addTransactionViewModel.addTransactionUiState.collectAsStateWithLifecycle().value

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = DateTimeUtils.currentInstant(TimeZone.UTC)
            .toEpochMilliseconds(),
    )
    val datePickerSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val categorySheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var selectedDate: Long? = null

    // Listen for selected item in transaction history via SavedStateHandle
    val selectedTransaction = navHostController.currentBackStackEntry
        ?.savedStateHandle
        ?.getStateFlow<Long?>(SelectedTransactionKey, null)?.collectAsStateWithLifecycle()?.value

    // show transaction bottom sheet with selected item
    LaunchedEffect(selectedTransaction) {
        selectedTransaction?.let {
            scaffoldState.bottomSheetState.expand()
        }
    }

    // clear data when bottom state not expanded
    LaunchedEffect(scaffoldState.bottomSheetState.currentValue) {
        if (scaffoldState.bottomSheetState.currentValue != SheetValue.Expanded) {
            addTransactionViewModel.resetSelectedTransaction()
            navHostController.currentBackStackEntry?.savedStateHandle?.set(
                SelectedTransactionKey,
                null
            )
        }
    }

    // close date picker
    LaunchedEffect(datePickerState.selectedDateMillis) {
        if (selectedDate != datePickerState.selectedDateMillis && datePickerSheetState.isVisible) {
            selectedDate =
                if (datePickerState.selectedDateMillis.toLocalDate() == DateTimeUtils.currentDate()) {
                    DateTimeUtils.currentInstant()
                        .toEpochMilliseconds()
                } else {
                    datePickerState.selectedDateMillis
                }
            addTransactionViewModel.onSelectedDate(selectedDate)
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
                modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars).fillMaxWidth(),
                viewModel = addTransactionViewModel,
                currentTransactionId = selectedTransaction,
                onEventLaunch = { event ->
                    handleAddTransactionEvent(
                        event,
                        scope,
                        datePickerSheetState,
                        categorySheetState,
                        onHideSheet = {
                            scope.launch {
                                scaffoldState.bottomSheetState.hide()
                            }
                        }
                    )
                },
            )
        },
    ) {
        Scaffold(bottomBar = {
            if (isTopLevelDestination && !showNavigationRail) {
                BottomNavBar(
                    bottomNavItems = topLevelDestinations,
                    currentRoute = currentDestination,
                    onItemSelected = { item ->
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
            BoxWithConstraints(
                modifier = Modifier.fillMaxSize()
                    .windowInsetsPadding(WindowInsets.safeDrawing)
            ) {
                screenSize = ScreenSize.basedOnWidth(this.maxWidth)

                Row(
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (isTopLevelDestination && showNavigationRail) {
                        NavRailBar(
                            items = topLevelDestinations,
                            currentRoute = currentDestination,
                            onItemSelected = {
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

                if (datePickerSheetState.isVisible) {
                    ModalBottomSheet(
                        sheetState = datePickerSheetState,
                        onDismissRequest = {
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

                if (categorySheetState.isVisible) {
                    ModalBottomSheet(
                        sheetState = categorySheetState,
                        onDismissRequest = {
                            scope.launch {
                                categorySheetState.hide()
                            }
                        }
                    ) {
                        CategorySheet(
                            categories = addTransactionUiState.categories,
                            addTransactionUiState.addTransaction.categorySelected,
                            onSelectedCategory = { category ->
                                addTransactionViewModel.selectCategory(category)
                                scope.launch {
                                    categorySheetState.hide()
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
private fun handleAddTransactionEvent(
    event: AddTransactionUiEvent,
    scope: CoroutineScope,
    datePickerSheetState: SheetState,
    categorySheetState: SheetState,
    onHideSheet: () -> Unit,
) {
    when (event) {
        AddTransactionUiEvent.HideSheet -> {
            onHideSheet()
        }

        AddTransactionUiEvent.ShowDatePicker -> {
            scope.launch { datePickerSheetState.expand() }
        }

        AddTransactionUiEvent.ExpandCategory -> {
            scope.launch { categorySheetState.expand() }
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
