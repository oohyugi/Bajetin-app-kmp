package com.bajetin.app.features.transaction.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
                modifier = Modifier.fillMaxWidth().clip(
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp
                    )
                )
                    .background(
                        color = MaterialTheme.colorScheme.surfaceColorAtElevation((0.1).dp),
                    ).padding(start = 16.dp, bottom = 32.dp, end = 16.dp, top = 16.dp)
            )
        }
        itemsIndexed(items = transactions) { index, transaction ->
            val category = transaction.category
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceBright),
                elevation = CardDefaults.elevatedCardElevation(1.dp),
                modifier = Modifier.offset(y = (-16).dp),
                shape = RoundedCornerShape(
                    topStart = if (index == 0) 16.dp else 0.dp,
                    topEnd = if (index == 0) 16.dp else 0.dp,
                    bottomEnd = if (index == transactions.lastIndex) 16.dp else 0.dp,
                    bottomStart = if (index == transactions.lastIndex) 16.dp else 0.dp
                )
            ) {
                TransactionItem(
                    category,
                    transaction,
                    textColor = getTextColor(date),
                    modifier = Modifier
                        .padding(16.dp)
                )
                HorizontalDivider(
                    modifier = Modifier.padding(
                        horizontal = 16.dp,
                    ),
                    thickness = (0.2).dp
                )
            }
        }
        item {
            Spacer(Modifier.size(8.dp))
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
