package com.bajetin.app.data.repository

import com.bajetin.app.data.entity.TransactionCategoryEntity
import com.bajetin.app.data.entity.TransactionEntity
import com.bajetin.app.data.local.TransactionLocalSource
import com.bajetin.app.domain.repository.TransactionRepo
import kotlinx.coroutines.flow.Flow

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
        notes: String
    ) {
        if (catId == null) return

        localSource.insertTransaction(
            catId,
            amount = amount.toLongOrNull() ?: 0,
            dateMillis = dateMillis ?: 0,
            notes
        )
    }

    override fun getAllTransactions(): Flow<List<TransactionEntity>> =
        localSource.getAllTransactions()
}
