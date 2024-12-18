package com.bajetin.app.features.transaction.presentation

import app.cash.turbine.test
import com.bajetin.app.core.UiState
import com.bajetin.app.core.utils.TimePeriod
import com.bajetin.app.data.entity.TransactionCategoryEntity
import com.bajetin.app.data.entity.TransactionEntity
import com.bajetin.app.data.entity.TransactionTotalEntity
import com.bajetin.app.features.main.presentation.TransactionRepoFake
import kotlinx.coroutines.test.runTest
import org.koin.test.KoinTest
import kotlin.test.Test
import kotlin.test.assertEquals

class TransactionViewModelTest : KoinTest {
    @Test
    fun `should return transactions`() = runTest {
        val mockTransactions = listOf(
            TransactionEntity(
                amount = 10000,
                notes = "Notes",
                updatedAt = 1625072400000L,
                category = TransactionCategoryEntity(emoticon = "", label = "Food"),
            )
        )
        val viewModel = TransactionViewModel(
            TransactionRepoFake(
                categories = TransactionCategoryEntity.initialCategories,
                transactions = mockTransactions,
                totalEntity = TransactionTotalEntity(1000, TimePeriod.Month)

            ),
        )

        viewModel.transactionUiState.test {
            assertEquals(TransactionUiState(), awaitItem())
            assertEquals(
                TransactionUiState(
                    totalSpentState = UiState.Success(
                        TransactionTotalEntity(
                            1000,
                            TimePeriod.Month
                        )
                    ),
                    groupedTransactions = UiState.Success(
                        listOf(
                            GroupedTransaction(
                                title = "01 Jul 21",
                                transactions = mockTransactions
                            )
                        )
                    )
                ),
                awaitItem()
            )
        }
    }

    @Test
    fun `should return empty transactions`() = runTest {
        val viewModel = TransactionViewModel(
            TransactionRepoFake(
                categories = TransactionCategoryEntity.initialCategories,
                transactions = emptyList()
            ),
        )

        viewModel.transactionUiState.test {
            assertEquals(TransactionUiState(), awaitItem())
        }
    }
}
