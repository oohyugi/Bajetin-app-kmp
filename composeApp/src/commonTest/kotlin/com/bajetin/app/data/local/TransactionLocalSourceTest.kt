package com.bajetin.app.data.local

import com.bajetin.app.core.utils.TimePeriod
import com.bajetin.app.data.entity.TransactionTotalEntity
import com.bajetin.app.data.entity.TransactionCategoryEntity
import com.bajetin.app.data.entity.TransactionType
import com.bajetin.app.di.coreModule
import com.bajetin.app.di.dataSourceModule
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
import kotlin.test.assertEquals

class TransactionLocalSourceTest : KoinTest {
    private val testDispatcher = StandardTestDispatcher()

    private val dataSource: TransactionLocalSource by inject()

    @BeforeTest
    fun setup() {
        startKoin(
            modules = listOf(testModule, coreModule, dataSourceModule),
            level = Level.NONE
        )
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `should insert and retrieve categories`() = runTest(testDispatcher) {
        dataSource.insertCategory("Food", "🍔")
        dataSource.insertCategory("Transport", "🚗")

        val categories = dataSource.getAllCategories().first()

        assertEquals("Food", categories[0].label)
        assertEquals("🍔", categories[0].emoticon)
        assertEquals("Transport", categories[1].label)
        assertEquals("🚗", categories[1].emoticon)
    }

    @Test
    fun `should return default categories`() = runTest(testDispatcher) {
        val categories = dataSource.getAllCategories().first()
        assertEquals(TransactionCategoryEntity.initialCategories.size, categories.size)
    }

    @Test
    fun `should insert and retrieve transactions`() = runTest(testDispatcher) {
        dataSource.insertCategory("Transport", "")
        dataSource.insertTransaction(1, 1000, 1625072400000L, "Kantor", TransactionType.Expense)

        val transactions = dataSource.getAllTransactions().first()

        assertEquals(1000, transactions[0].amount)
        assertEquals(1625072400000L, transactions[0].updatedAt)
        assertEquals("Kantor", transactions[0].notes)
        assertEquals("Transport", transactions[0].category?.label)
    }

    @Test
    fun `should return total spending by period`() = runTest(testDispatcher) {
        setupTestDataForTestTotalSpending()
        val currentDateInMillis = 1734504531000 // Wednesday, 18 December 2024

        val expectedTotals = getExpectedTotals()

        TimePeriod.entries.forEach { timePeriod ->
            val spending = dataSource.getTotal(
                period = timePeriod,
                currentDateInMillis = currentDateInMillis,
                transactionType = TransactionType.Expense
            )
            val expectedTotal = expectedTotals[timePeriod] ?: 0L
            assertEquals(
                TransactionTotalEntity(
                    timePeriod = timePeriod,
                    totalAmount = expectedTotal
                ),
                actual = spending
            )
        }
    }

    @Test
    fun `should return total income by period`() = runTest(testDispatcher) {
        setupTestDataForTestTotalSpending()
        val currentDateInMillis = 1734504531000 // Wednesday, 18 December 2024
        val period = TimePeriod.Year

        val spending = dataSource.getTotal(
            period = period,
            currentDateInMillis = currentDateInMillis,
            transactionType = TransactionType.Income
        )

        assertEquals(
            TransactionTotalEntity(
                timePeriod = period,
                totalAmount = 20000
            ),
            actual = spending
        )
    }

    @Test
    fun `getSummary should return correct number of summaries`() = runTest(testDispatcher) {
        val timePeriod = TimePeriod.Month
        val transactionType = TransactionType.Expense
        val currentDateInMillis = 1734504531000L // Wednesday, 18 December 2024
        setupTestDataForTestTotalSpending()
        val summary = dataSource.getSummary(
            period = timePeriod,
            currentDateInMillis = currentDateInMillis,
            transactionType
        ).first()
        assertEquals(2, actual = summary.size)
    }

    private suspend fun setupTestDataForTestTotalSpending() {
        dataSource.insertCategory("Food", "🍔")
        dataSource.insertCategory("Transport", "trans")

        dataSource.insertTransaction(
            1,
            10000,
            1625072400000, // Thursday, 1 July 2021
            "Nasi Padang",
            TransactionType.Expense
        )
        dataSource.insertTransaction(
            1,
            15000,
            1734504531000, // Wednesday, 18 December 2024
            "Cokelat",
            TransactionType.Expense
        )
        dataSource.insertTransaction(
            2,
            20000,
            1734418131000, // Thursday, 17 December 2024
            "Trans jakarta",
            TransactionType.Expense
        )
        dataSource.insertTransaction(
            1,
            20000,
            1733035731000, // Sunday, 1 December 2024
            "Mangga",
            TransactionType.Expense
        )

        dataSource.insertTransaction(
            1,
            20000,
            1711954131000, // Monday, 1 April 2024
            "Jeruk",
            TransactionType.Expense
        )
        dataSource.insertTransaction(
            1,
            20000,
            1711954131000, // Monday, 1 April 2024
            "Dana masuk",
            TransactionType.Income
        )
    }

    private fun getExpectedTotals(): Map<TimePeriod, Long> {
        return mapOf(
            TimePeriod.Day to 15000,
            TimePeriod.Week to 35000,
            TimePeriod.Month to 55000,
            TimePeriod.Year to 75000,
            TimePeriod.AllTime to 85000
        )
    }
}
