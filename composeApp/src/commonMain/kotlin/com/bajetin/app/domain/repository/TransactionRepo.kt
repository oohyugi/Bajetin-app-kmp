package com.bajetin.app.domain.repository

import com.bajetin.app.data.entity.TransactionCategoryEntity
import com.bajetin.app.data.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

interface TransactionRepo {
    suspend fun insertCategory(label: String, emoticon: String?)
    fun getAllCategories(): Flow<List<TransactionCategoryEntity>>
    suspend fun insertTransaction(catId: Long?, amount: String, dateMillis: Long?, notes: String)
    fun getAllTransactions(): Flow<List<TransactionEntity>>
}
