package com.bajetin.app.data.repository

import com.bajetin.app.data.entity.TransactionCategoryEntity
import com.bajetin.app.data.local.TransactionCategoryDataSource
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class TransactionCategoryRepoImplTest {

    private val testDispatcher = StandardTestDispatcher()

    private val dataSource = object : TransactionCategoryDataSource {
        private val categories = mutableListOf<TransactionCategoryEntity>()

        override suspend fun insert(label: String, emoticon: String?) {
            categories.add(TransactionCategoryEntity(categories.size.toLong(), emoticon, label))
        }

        override fun getAll(): Flow<List<TransactionCategoryEntity>> {
            return flowOf(categories)
        }
    }

    private val repo = TransactionCategoryRepoImpl(dataSource)

    @Test
    fun `should insert adds a new category`() = runTest(testDispatcher) {
        repo.insert("Food", "🍔")

        val categories = repo.getAll().first()

        // Assert the category is added
        assertEquals(1, categories.size)
        assertEquals("Food", categories[0].label)
        assertEquals("🍔", categories[0].emoticon)
    }

    @Test
    fun `should retrieves all categories`() = runTest(testDispatcher) {
        repo.insert("Food", "🍔")
        repo.insert("Transport", "🚗")

        val categories = repo.getAll().first()

        // Assert all categories are retrieved
        assertEquals(2, categories.size)
        assertEquals("Food", categories[0].label)
        assertEquals("🍔", categories[0].emoticon)
        assertEquals("Transport", categories[1].label)
        assertEquals("🚗", categories[1].emoticon)
    }
}
