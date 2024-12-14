package com.bajetin.app.data.local

import com.bajetin.app.data.entity.TransactionCategoryEntity
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
        dataSource.insertTransaction(1, 1000, 1625072400000L, "Kantor")

        val transactions = dataSource.getAllTransactions().first()

        assertEquals(1000, transactions[0].amount)
        assertEquals(1625072400000L, transactions[0].updatedAt)
        assertEquals("Kantor", transactions[0].notes)
        assertEquals("Transport", transactions[0].category?.label)
    }
}
