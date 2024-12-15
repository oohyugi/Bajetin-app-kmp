package com.bajetin.app.features.transactionHistory.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bajetin.app.core.utils.toDisplayDate
import com.bajetin.app.domain.repository.TransactionRepo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class TransactionHistoryViewModel(
    private val transactionRepo: TransactionRepo,
) : ViewModel() {

    /**
     * Retrieves all transactions from the repository, groups them by their `updatedAt` date,
     * and maps the result to a list of `TransactionHistoryUiState` objects. The resulting state
     */
    val transactions = transactionRepo.getAllTransactions()
        .map { transactions ->
            transactions.groupBy { transaction -> transaction.updatedAt?.toDisplayDate() }.map {
                TransactionHistoryUiState(title = it.key ?: "", transactions = it.value)
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )
}
