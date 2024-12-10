package com.bajetin.app.data.repository

import com.bajetin.app.data.entity.TransactionCategoryEntity
import com.bajetin.app.data.local.TransactionCategoryDataSource
import kotlinx.coroutines.flow.Flow

interface TransactionCategoryRepo {
    suspend fun insert(label: String, emoticon: String?)
    fun getAll(): Flow<List<TransactionCategoryEntity>>
}

class TransactionCategoryRepoImpl(
    private val localSource: TransactionCategoryDataSource,
) :
    TransactionCategoryRepo {
    override suspend fun insert(label: String, emoticon: String?) =
        localSource.insert(label, emoticon)

    override fun getAll(): Flow<List<TransactionCategoryEntity>> = localSource.getAll()
}
