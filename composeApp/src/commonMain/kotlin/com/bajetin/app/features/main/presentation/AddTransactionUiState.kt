package com.bajetin.app.features.main.presentation

import com.bajetin.app.data.entity.TransactionCategoryEntity
import kotlinx.serialization.Serializable

data class AddTransactionUiState(
    val addTransaction: AddTransactionModel = AddTransactionModel(),
    val categories: List<TransactionCategoryEntity> = emptyList()
)

@Serializable
data class AddTransactionModel(
    val id: Long? = null,
    val expression: String = "",
    val amount: String = "0",
    val isAmountCleared: Boolean = false,
    val dateMillis: Long? = null,
    val categorySelected: TransactionCategoryEntity? = null,
    val notes: String = ""
)
