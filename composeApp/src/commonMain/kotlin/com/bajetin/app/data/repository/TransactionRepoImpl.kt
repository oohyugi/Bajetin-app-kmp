package com.bajetin.app.data.repository

import com.bajetin.app.data.entity.TransactionCategoryEntity
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
}
