package com.bajetin.app.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class TransactionEntity(
    val id: Long = 0,
    val category: TransactionCategoryEntity? = null,
    val amount: Long = 0,
    val updatedAt: Long?,
    val notes: String? = null,
    val type: TransactionType = TransactionType.Expense
)
