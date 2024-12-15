package com.bajetin.app.features.transactionHistory.presentation

import app.cash.turbine.test
import com.bajetin.app.data.entity.TransactionCategoryEntity
import com.bajetin.app.data.entity.TransactionEntity
import com.bajetin.app.features.main.presentation.TransactionRepoFake
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class TransactionHistoryViewModelTest {
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
        val viewModel = TransactionHistoryViewModel(
            TransactionRepoFake(
                categories = TransactionCategoryEntity.initialCategories,
                transactions = mockTransactions
            ),
        )

        viewModel.transactions.test {
            assertEquals(emptyList(), awaitItem())
            assertEquals(
                listOf(TransactionHistoryUiState("01 Jul 21", mockTransactions)),
                awaitItem()
            )
        }
    }

    @Test
    fun `should return empty transactions`() = runTest {
        val mockTransactions = listOf<TransactionEntity>()
        val viewModel = TransactionHistoryViewModel(
            TransactionRepoFake(
                categories = TransactionCategoryEntity.initialCategories,
                transactions = mockTransactions
            ),
        )

        viewModel.transactions.test {
            assertEquals(emptyList(), awaitItem())
        }
    }
}
