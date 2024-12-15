package com.bajetin.app.features.transactionHistory.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.bajetin.app.features.transactionHistory.presentation.component.HeaderItem
import com.bajetin.app.features.transactionHistory.presentation.component.TransactionItem
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionHistoryScreen(viewModel: TransactionHistoryViewModel) {
    val transactions = viewModel.transactions.collectAsStateWithLifecycle().value
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.transaction_title)) }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(it),
            contentPadding = PaddingValues(16.dp),
        ) {
            transactions.forEach { (date, transactions) ->
                item {
                    HeaderItem(
                        date,
                        textColor = getTextColor(date),
                        modifier = Modifier.padding(bottom = 16.dp)
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
