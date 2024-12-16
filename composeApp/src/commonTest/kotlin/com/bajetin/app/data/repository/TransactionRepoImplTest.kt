package com.bajetin.app.data.repository

import com.bajetin.app.core.utils.TimePeriod
import com.bajetin.app.data.entity.TransactionTotalEntity
import com.bajetin.app.data.entity.TransactionCategoryEntity
import com.bajetin.app.data.entity.TransactionEntity
import com.bajetin.app.data.entity.TransactionSummaryEntity
import com.bajetin.app.data.entity.TransactionType
import com.bajetin.app.data.local.TransactionLocalSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class TransactionRepoImplTest {

    private val testDispatcher = StandardTestDispatcher()

    private val dataSource = object : TransactionLocalSource {
        private val categories = mutableListOf<TransactionCategoryEntity>()

        private val transactions = mutableListOf<TransactionEntity>()

        override suspend fun insertCategory(label: String, emoticon: String?) {
            categories.add(TransactionCategoryEntity(categories.size.toLong(), emoticon, label))
        }

        override fun getAllCategories(): Flow<List<TransactionCategoryEntity>> {
            return flowOf(categories)
        }

        override suspend fun insertTransaction(
            catId: Long,
            amount: Long,
            dateMillis: Long,
            notes: String,
            transactionType: TransactionType,
        ) {
            transactions.add(
                TransactionEntity(
                    id = transactions.size.toLong(),
                    category = categories.find { category -> category.id == catId },
                    amount = amount,
                    updatedAt = dateMillis,
                    notes = notes
                )
            )
        }

        override fun getAllTransactions(): Flow<List<TransactionEntity>> {
            return flowOf(transactions)
        }

        override fun getTotal(
            period: TimePeriod,
            currentDateInMillis: Long,
            transactionType: TransactionType,
        ): Flow<TransactionTotalEntity> {
            return flowOf(TransactionTotalEntity(0, TimePeriod.DAY))
        }

        override fun getSummary(
            period: TimePeriod,
            currentDateInMillis: Long,
            transactionType: TransactionType
        ): Flow<List<TransactionSummaryEntity>> {
            return flowOf()
        }
    }

    private val repo = TransactionRepoImpl(dataSource)

    @Test
    fun `should insert adds a new category`() = runTest(testDispatcher) {
        repo.insertCategory("Food", "🍔")

        val categories = repo.getAllCategories().first()

        // Assert the category is added
        assertEquals(1, categories.size)
        assertEquals("Food", categories[0].label)
        assertEquals("🍔", categories[0].emoticon)
    }

    @Test
    fun `should retrieves all categories`() = runTest(testDispatcher) {
        repo.insertCategory("Food", "🍔")
        repo.insertCategory("Transport", "🚗")

        val categories = repo.getAllCategories().first()

        // Assert all categories are retrieved
        assertEquals(2, categories.size)
        assertEquals("Food", categories[0].label)
        assertEquals("🍔", categories[0].emoticon)
        assertEquals("Transport", categories[1].label)
        assertEquals("🚗", categories[1].emoticon)
    }

    @Test
    fun `should insert adds a new transactions`() = runTest(testDispatcher) {
        repo.insertCategory("Transport", "🚗")
        repo.insertTransaction(0, amount = "10000", 1625072400000L, notes = "Kantor")

        val transactions = repo.getAllTransactions().first()

        assertEquals(1, transactions.size)
        assertEquals(10000, transactions[0].amount)
        assertEquals("Kantor", transactions[0].notes)
        assertEquals(1625072400000L, transactions[0].updatedAt)
        assertEquals("Transport", transactions[0].category?.label)
    }
}
