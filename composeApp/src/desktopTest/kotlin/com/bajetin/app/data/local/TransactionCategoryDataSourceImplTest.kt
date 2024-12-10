package com.bajetin.app.data.local

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.bajetin.app.data.entity.TransactionCategoryEntity
import com.bajetin.app.db.BajetinDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class TransactionCategoryDataSourceImplTest {

    private val testDispatcher = StandardTestDispatcher()
    private val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).also {
        BajetinDatabase.Schema.create(it)
    }
    private val database = BajetinDatabase(driver)
    private val dataSource = TransactionCategoryDataSourceImpl(database, testDispatcher)

    @Test
    fun `should insert and retrieve categories`() = runTest(testDispatcher) {
        dataSource.insert("Food", "🍔")
        dataSource.insert("Transport", "🚗")

        val categories = dataSource.getAll().first()

        assertEquals("Food", categories[0].label)
        assertEquals("🍔", categories[0].emoticon)
        assertEquals("Transport", categories[1].label)
        assertEquals("🚗", categories[1].emoticon)
    }

    @Test
    fun `should return default categories`() = runTest(testDispatcher) {
        val categories = dataSource.getAll().first()
        assertEquals(TransactionCategoryEntity.initialCategories.size, categories.size)
    }
}
