package com.bajetin.app.features.transactionHistory.presentation

import com.bajetin.app.data.entity.TransactionEntity

data class TransactionHistoryUiState(
    val title: String,
    val transactions: List<TransactionEntity>
)
