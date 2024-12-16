package com.bajetin.app.data.repository

import com.bajetin.app.core.utils.TimePeriod
import com.bajetin.app.data.entity.TransactionCategoryEntity
import com.bajetin.app.data.entity.TransactionSummaryEntity
import com.bajetin.app.data.entity.TransactionTotalEntity
import com.bajetin.app.data.entity.TransactionType
import com.bajetin.app.di.coreModule
import com.bajetin.app.di.dataSourceModule
import com.bajetin.app.di.repositoryModule
import com.bajetin.app.domain.repository.TransactionRepo
import com.bajetin.app.testModule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.koin.core.logger.Level
import org.koin.mp.KoinPlatform.startKoin
import org.koin.mp.KoinPlatform.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class TransactionRepoImplTest : KoinTest {

    private val testDispatcher = StandardTestDispatcher()

    private val repo: TransactionRepo by inject()

    @BeforeTest
    fun setup() {
        startKoin(
            modules = listOf(testModule, coreModule, dataSourceModule, repositoryModule),
            level = Level.NONE
        )
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

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
        repo.insertTransaction(1, amount = "10000", 1625072400000L, notes = "Kantor")

        val transactions = repo.getAllTransactions().first()

        assertEquals(1, transactions.size)
        assertEquals(10000, transactions[0].amount)
        assertEquals("Kantor", transactions[0].notes)
        assertEquals(1625072400000L, transactions[0].updatedAt)
        assertEquals("Transport", transactions[0].category?.label)
    }

    @Test
    fun `should get total spending transactions`() = runTest(testDispatcher) {
        repo.insertCategory("Transport", "🚗")
        repo.insertTransaction(0, amount = "10000", 1625072400000L, notes = "Kantor")

        val transactions =
            repo.getTotalTransactions(TimePeriod.Day, 1625072400000L, TransactionType.Expense)
                .first()

        assertEquals(TransactionTotalEntity(10000, TimePeriod.Day), transactions)
    }

    @Test
    fun `should get summary spending transactions`() = runTest(testDispatcher) {
        repo.insertCategory("Transport", "🚗")
        repo.insertTransaction(1, amount = "10000", 1625072400000L, notes = "Kantor")

        val transactions =
            repo.getSummaryTransactions(TimePeriod.Day, 1625072400000L, TransactionType.Expense)
                .first()

        assertContains(
            transactions,
            TransactionSummaryEntity(
                TransactionCategoryEntity(
                    id = 1,
                    emoticon = "\uD83D\uDE97",
                    label = "Transport"
                ),
                10000,
                TimePeriod.Day,
            )
        )
    }
}
