package com.bajetin.app.features.transaction.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import bajetin.composeapp.generated.resources.Res
import bajetin.composeapp.generated.resources.transaction_title
import bajetin.composeapp.generated.resources.upcoming_title
import com.bajetin.app.core.UiState
import com.bajetin.app.data.entity.TransactionTotalEntity
import com.bajetin.app.features.transaction.presentation.component.HeaderItem
import com.bajetin.app.features.transaction.presentation.component.TotalSpentItem
import com.bajetin.app.features.transaction.presentation.component.TransactionItem
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionHistoryScreen(viewModel: TransactionViewModel) {
    val transactionUiState = viewModel.transactionUiState.collectAsStateWithLifecycle().value
    val lazyListState = rememberLazyListState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.transaction_title)) },
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize().consumeWindowInsets(it)) {
            LazyColumn(
                state = lazyListState,
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 64.dp),
                modifier = Modifier.padding(it)
            ) {
                totalSpentStateItem(
                    transactionUiState.totalSpentState,
                    onPeriodClick = viewModel::changeTimePeriod,
                    modifier = Modifier,
                )
                groupedTransactionsStateItem(transactionUiState.groupedTransactions)
            }
        }
    }
}

private fun LazyListScope.totalSpentStateItem(
    state: UiState<TransactionTotalEntity>,
    onPeriodClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    item {
        Box(modifier) {
            when (state) {
                is UiState.Success -> {
                    TotalSpentItem(state.result, onPeriodClick)
                }

                else -> {} // Handle Loading and Error
            }
        }
    }
}

private fun LazyListScope.groupedTransactionsStateItem(state: UiState<List<GroupedTransaction>>) {
    when (state) {
        is UiState.Success -> {
            transactionItems(state.result)
        }

        else -> {} // Handle Loading and Error
    }
}

private fun LazyListScope.transactionItems(groupedTransaction: List<GroupedTransaction>) {
    groupedTransaction.forEach { (date, transactions) ->
        item {
            HeaderItem(
                date,
                textColor = getTextColor(date),
                modifier = Modifier.padding(bottom = 16.dp, top = 8.dp)
            )
        }
        items(items = transactions) { transaction ->
            val category = transaction.category
            TransactionItem(
                category,
                transaction,
                textColor = getTextColor(date),
                modifier = Modifier.padding(start = 16.dp)

            )
            HorizontalDivider(
                modifier = Modifier.padding(
                    start = 16.dp,
                    top = 12.dp,
                    bottom = 12.dp
                ),
                thickness = (0.5).dp
            )
        }
        item {
            Spacer(Modifier.size(12.dp))
        }
    }
}

@Composable
private fun getTextColor(date: String?): Color {
    val isUpcoming = date?.contains(
        stringResource(Res.string.upcoming_title), ignoreCase = true
    ) == true
    return MaterialTheme.colorScheme.onSurface.copy(
        if (isUpcoming) 0.5F else 1F
    )
}
