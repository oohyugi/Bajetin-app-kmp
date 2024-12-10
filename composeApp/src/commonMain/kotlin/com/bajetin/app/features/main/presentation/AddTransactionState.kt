package com.bajetin.app.features.main.presentation

import com.bajetin.app.core.utils.Constants.operators
import kotlinx.serialization.Serializable

@Serializable
data class AddTransactionState(
    val expression: String = "",
    val transactionAmount: String = "0",
) {

    fun isExpressionContainsAnyOperator() = operators.any { expression.contains(it) }
}
