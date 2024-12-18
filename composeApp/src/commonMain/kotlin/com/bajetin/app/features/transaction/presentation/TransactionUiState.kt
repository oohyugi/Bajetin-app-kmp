package com.bajetin.app.features.transaction.presentation

import com.bajetin.app.core.UiState
import com.bajetin.app.data.entity.TransactionEntity
import com.bajetin.app.data.entity.TransactionTotalEntity

data class TransactionUiState(
    val totalSpentState: UiState<TransactionTotalEntity> = UiState.Loading,
    val groupedTransactions: UiState<List<GroupedTransaction>> = UiState.Loading,
)

data class GroupedTransaction(
    val title: String,
    val transactions: List<TransactionEntity>
)
