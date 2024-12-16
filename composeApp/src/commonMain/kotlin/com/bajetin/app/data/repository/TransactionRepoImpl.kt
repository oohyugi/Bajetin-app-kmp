package com.bajetin.app.data.repository

import com.bajetin.app.core.utils.TimePeriod
import com.bajetin.app.data.entity.TransactionCategoryEntity
import com.bajetin.app.data.entity.TransactionEntity
import com.bajetin.app.data.entity.TransactionSummaryEntity
import com.bajetin.app.data.entity.TransactionTotalEntity
import com.bajetin.app.data.entity.TransactionType
import com.bajetin.app.data.local.TransactionLocalSource
import com.bajetin.app.domain.repository.TransactionRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock

class TransactionRepoImpl(
    private val localSource: TransactionLocalSource,
) :
    TransactionRepo {
    override suspend fun insertCategory(label: String, emoticon: String?) =
        localSource.insertCategory(label, emoticon)

    override fun getAllCategories(): Flow<List<TransactionCategoryEntity>> =
        localSource.getAllCategories()

    override suspend fun insertTransaction(
        catId: Long?,
        amount: String,
        dateMillis: Long?,
        notes: String,
        transactionType: TransactionType
    ) {
        if (catId == null) return

        localSource.insertTransaction(
            catId,
            amount = amount.toLongOrNull() ?: 0,
            dateMillis = dateMillis ?: Clock.System.now().toEpochMilliseconds(),
            notes = notes,
            transactionType = transactionType
        )
    }

    override fun getAllTransactions(): Flow<List<TransactionEntity>> =
        localSource.getAllTransactions()

    override fun getTotalTransactions(
        timePeriod: TimePeriod,
        dateMillis: Long,
        transactionType: TransactionType
    ): Flow<TransactionTotalEntity> = localSource.getTotal(timePeriod, dateMillis, transactionType)

    override fun getSummaryTransactions(
        timePeriod: TimePeriod,
        dateMillis: Long,
        transactionType: TransactionType
    ): Flow<List<TransactionSummaryEntity>> =
        localSource.getSummary(timePeriod, dateMillis, transactionType)
}
