package com.bajetin.app.features.transaction.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bajetin.app.core.UiState
import com.bajetin.app.core.utils.TimePeriod
import com.bajetin.app.core.utils.toDisplayDate
import com.bajetin.app.data.entity.TransactionEntity
import com.bajetin.app.data.entity.TransactionType
import com.bajetin.app.domain.repository.TransactionRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.datetime.Clock

class TransactionViewModel(
    private val transactionRepo: TransactionRepo,
) : ViewModel() {

    private var _timePeriod = MutableStateFlow(TimePeriod.AllTime)

    @OptIn(ExperimentalCoroutinesApi::class)
    val transactionUiState: StateFlow<TransactionUiState> =
        _timePeriod.combine(transactionRepo.getAllTransactions()) { timePeriod, transactions ->
            Pair(timePeriod, transactions)
        }.flatMapLatest { (period, transactions) ->
            flow {
                val grouped = groupTransactions(transactions)

                val total = transactionRepo.getTotalTransactions(
                    timePeriod = period,
                    dateMillis = Clock.System.now().toEpochMilliseconds(),
                    transactionType = TransactionType.Expense
                )
                emit(
                    TransactionUiState(
                        totalSpentState = UiState.Success(total),
                        groupedTransactions = UiState.Success(grouped)
                    )
                )
            }
        }
            .catch { e ->
                emit(
                    TransactionUiState(
                        totalSpentState = UiState.Error(e.message.orEmpty()),
                        groupedTransactions = UiState.Error(e.message.orEmpty())
                    )
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = TransactionUiState(
                    totalSpentState = UiState.Loading,
                    groupedTransactions = UiState.Loading
                )
            )

    fun changeTimePeriod() {
        _timePeriod.update { currentPeriod ->
            val currentIndex = TimePeriod.entries.indexOf(currentPeriod)
            val nextIndex = (currentIndex + 1) % TimePeriod.entries.size
            TimePeriod.entries[nextIndex]
        }
    }

    private fun groupTransactions(transactions: List<TransactionEntity>): List<GroupedTransaction> {
        return transactions.groupBy { it.updatedAt?.toDisplayDate() }
            .map {
                GroupedTransaction(
                    title = it.key ?: "Unknown Date",
                    transactions = it.value
                )
            }
    }
}
