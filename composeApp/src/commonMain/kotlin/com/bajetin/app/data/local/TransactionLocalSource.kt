package com.bajetin.app.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.bajetin.app.core.utils.DateTimeUtils
import com.bajetin.app.core.utils.TimePeriod
import com.bajetin.app.core.utils.calculateTimeRange
import com.bajetin.app.data.entity.TransactionCategoryEntity
import com.bajetin.app.data.entity.TransactionEntity
import com.bajetin.app.data.entity.TransactionSummaryEntity
import com.bajetin.app.data.entity.TransactionTotalEntity
import com.bajetin.app.data.entity.TransactionType
import com.bajetin.app.db.BajetinDatabase
import com.bajetin.app.db.Categories
import com.bajetin.app.db.SelectAllTransactionsWithCategory
import com.bajetin.app.db.SelectSummaryBetween
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.datetime.Instant

interface TransactionLocalSource {
    suspend fun insertCategory(label: String, emoticon: String?)
    fun getAllCategories(): Flow<List<TransactionCategoryEntity>>

    suspend fun insertTransaction(
        catId: Long,
        amount: Long,
        dateMillis: Long,
        notes: String,
        transactionType: TransactionType,
    )

    fun getAllTransactions(): Flow<List<TransactionEntity>>

    suspend fun getTotal(
        period: TimePeriod,
        currentDateInMillis: Long,
        transactionType: TransactionType,
    ): TransactionTotalEntity

    fun getSummary(
        period: TimePeriod,
        currentDateInMillis: Long,
        transactionType: TransactionType,
    ): Flow<List<TransactionSummaryEntity>>

    suspend fun removeTransaction(id: Long)
    suspend fun updateTransaction(transaction: TransactionEntity)
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
        transactionType: TransactionType
    ) =
        withContext(ioDispatcher) {
            queries.insertTransaction(
                note = notes,
                amount = amount,
                category_id = catId,
                type = transactionType.name,
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

    override suspend fun getTotal(
        period: TimePeriod,
        currentDateInMillis: Long,
        transactionType: TransactionType,
    ): TransactionTotalEntity {
        return withContext(ioDispatcher) {
            val (startMillis, endMillis) = calculateTimeRange(
                period = period,
                currentInstant = Instant.fromEpochMilliseconds(currentDateInMillis)
            )
            val result = queries.selectTotalTransactionBetween(
                transactionType.name,
                startMillis,
                endMillis
            ).executeAsOneOrNull()
            TransactionTotalEntity(result?.total?.toLong() ?: 0, period)
        }
    }

    override fun getSummary(
        period: TimePeriod,
        currentDateInMillis: Long,
        transactionType: TransactionType
    ): Flow<List<TransactionSummaryEntity>> {
        val (startMillis, endMillis) = calculateTimeRange(
            period = period,
            currentInstant = Instant.fromEpochMilliseconds(currentDateInMillis)
        )
        return queries.selectSummaryBetween(transactionType.name, startMillis, endMillis).asFlow()
            .mapToList(ioDispatcher)
            .map { summary ->
                summary.map {
                    mapSummaryEntity(it, period)
                }
            }
    }

    override suspend fun removeTransaction(id: Long) {
        withContext(ioDispatcher) {
            queries.deleteTransactions(id)
        }
    }

    override suspend fun updateTransaction(transaction: TransactionEntity) {
        withContext(ioDispatcher) {
            queries.updateTransaction(
                id = transaction.id,
                note = transaction.notes,
                amount = transaction.amount,
                categoryId = transaction.category?.id ?: -1,
                type = transaction.type.name,
                updatedAt = DateTimeUtils.currentInstant().toEpochMilliseconds()
            )
        }
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
            notes = transactionsWithCategories.note,
            type = TransactionType.valueOf(transactionsWithCategories.type)
        )

    private fun mapSummaryEntity(
        selectSummaryBetween: SelectSummaryBetween,
        timePeriod: TimePeriod
    ) =
        with(selectSummaryBetween) {
            TransactionSummaryEntity(
                category = TransactionCategoryEntity(id ?: 0, emoticon, label.orEmpty()),
                totalAmount = (total_amount ?: 0).toLong(),
                timePeriod = timePeriod
            )
        }
}
