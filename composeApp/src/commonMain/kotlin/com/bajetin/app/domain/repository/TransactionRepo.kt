package com.bajetin.app.domain.repository

import com.bajetin.app.core.utils.TimePeriod
import com.bajetin.app.data.entity.TransactionCategoryEntity
import com.bajetin.app.data.entity.TransactionEntity
import com.bajetin.app.data.entity.TransactionSummaryEntity
import com.bajetin.app.data.entity.TransactionTotalEntity
import com.bajetin.app.data.entity.TransactionType
import kotlinx.coroutines.flow.Flow

interface TransactionRepo {
    suspend fun insertCategory(label: String, emoticon: String?)
    fun getAllCategories(): Flow<List<TransactionCategoryEntity>>
    suspend fun insertTransaction(
        catId: Long?,
        amount: String,
        dateMillis: Long?,
        notes: String,
        transactionType: TransactionType = TransactionType.Expense,
    )

    fun getAllTransactions(): Flow<List<TransactionEntity>>

    suspend fun getTotalTransactions(
        timePeriod: TimePeriod,
        dateMillis: Long,
        transactionType: TransactionType
    ): TransactionTotalEntity

    fun getSummaryTransactions(
        timePeriod: TimePeriod,
        dateMillis: Long,
        transactionType: TransactionType
    ): Flow<List<TransactionSummaryEntity>>

    suspend fun removeTransaction(id: Long)

    suspend fun updateTransaction(transaction: TransactionEntity)

    fun getTransaction(id: Long): Flow<TransactionEntity?>
}
