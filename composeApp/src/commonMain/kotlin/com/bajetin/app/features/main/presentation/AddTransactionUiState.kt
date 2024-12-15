package com.bajetin.app.features.main.presentation

import com.bajetin.app.data.entity.TransactionCategoryEntity
import kotlinx.serialization.Serializable

@Serializable
data class AddTransactionUiState(
    val expression: String = "",
    val amount: String = "0",
    val isAmountCleared: Boolean = false,
    val dateMillis: Long? = null,
    val categorySelected: TransactionCategoryEntity? = null,
    val notes: String = ""
)
