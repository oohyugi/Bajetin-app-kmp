package com.bajetin.app.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.bajetin.app.data.entity.TransactionCategoryEntity
import com.bajetin.app.data.entity.TransactionEntity
import com.bajetin.app.db.BajetinDatabase
import com.bajetin.app.db.Categories
import com.bajetin.app.db.SelectAllTransactionsWithCategory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

interface TransactionLocalSource {
    suspend fun insertCategory(label: String, emoticon: String?)
    fun getAllCategories(): Flow<List<TransactionCategoryEntity>>

    suspend fun insertTransaction(catId: Long, amount: Long, dateMillis: Long, notes: String)
    fun getAllTransactions(): Flow<List<TransactionEntity>>
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
     * If the database is empty, inserts default categories and returns the updated list.
     * Uses `asFlow()` to observe real-time updates from the database.
     * @return A [Flow] emitting a list of transaction categories.
     */
    override fun getAllCategories(): Flow<List<TransactionCategoryEntity>> =
        queries.selectAllCategories()
            .asFlow()
            .mapToList(ioDispatcher)
            .map { categories ->
                if (categories.isEmpty()) {
                    insertDefaultCategories()
                    TransactionCategoryEntity.initialCategories
                } else {
                    categories.map(::mapCategoryEntity)
                }
            }

    override suspend fun insertTransaction(
        catId: Long,
        amount: Long,
        dateMillis: Long,
        notes: String,
    ) =
        withContext(ioDispatcher) {
            queries.insertTransaction(
                note = notes,
                amount = amount,
                category_id = catId,
                type = "expense",
                updated_at = dateMillis,
                created_at = dateMillis,
            )
        }

    override fun getAllTransactions(): Flow<List<TransactionEntity>> =
        queries.selectAllTransactionsWithCategory()
            .asFlow()
            .mapToList(ioDispatcher)
            .map { transactions ->
                transactions.map(::mapTransactionEntity)
            }

    private suspend fun insertDefaultCategories() {
        TransactionCategoryEntity.initialCategories.forEach {
            insertCategory(it.label, it.emoticon)
        }
    }

    private fun mapCategoryEntity(category: Categories) = TransactionCategoryEntity(
        id = category.id,
        emoticon = category.emoticon,
        label = category.label
    )

    private fun mapTransactionEntity(transactionsWithCategories: SelectAllTransactionsWithCategory) =
        TransactionEntity(
            id = transactionsWithCategories.id,
            category = TransactionCategoryEntity(
                transactionsWithCategories.id,
                transactionsWithCategories.emoticon,
                transactionsWithCategories.label.orEmpty()
            ),
            updatedAt = transactionsWithCategories.updated_at,
            amount = transactionsWithCategories.amount ?: 0,
            notes = transactionsWithCategories.note
        )
}
