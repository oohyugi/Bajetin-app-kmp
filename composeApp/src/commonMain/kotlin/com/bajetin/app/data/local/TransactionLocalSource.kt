package com.bajetin.app.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.bajetin.app.data.entity.TransactionCategoryEntity
import com.bajetin.app.db.BajetinDatabase
import com.bajetin.app.db.Categories
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

interface TransactionLocalSource {
    suspend fun insertCategory(label: String, emoticon: String?)
    fun getAllCategories(): Flow<List<TransactionCategoryEntity>>
}

class TransactionLocalSourceImpl(
    db: BajetinDatabase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

) : TransactionLocalSource {

    private val queries = db.bajetinDatabaseQueries
    override suspend fun insertCategory(label: String, emoticon: String?) =
        withContext(ioDispatcher) {
            queries.insertCategory(label, emoticon = emoticon)
        }

    /**
     * Retrieves all transaction categories as a [Flow] of [List] of [TransactionCategoryEntity].
     * [handleCategoriesResult] If the database is empty, inserts default categories and returns the updated list.
     * Uses `asFlow()` to observe real-time updates from the database.
     * @return A [Flow] emitting a list of transaction categories.
     */
    override fun getAllCategories(): Flow<List<TransactionCategoryEntity>> {
        return queries.selectAllCategories()
            .asFlow()
            .mapToList(ioDispatcher)
            .map { categories ->
                handleCategoriesResult(categories)
            }
    }

    private suspend fun handleCategoriesResult(
        categories: List<Categories>
    ) = if (categories.isEmpty()) {
        insertDefaultCategories()
        queries.selectAllCategories().executeAsList().map { category ->
            TransactionCategoryEntity(
                category.id,
                category.emoticon,
                category.label
            )
        }
    } else {
        categories.map { category ->
            TransactionCategoryEntity(
                category.id,
                category.emoticon,
                category.label
            )
        }
    }

    private suspend fun insertDefaultCategories() {
        TransactionCategoryEntity.initialCategories.forEach {
            insertCategory(it.label, it.emoticon)
        }
    }
}
