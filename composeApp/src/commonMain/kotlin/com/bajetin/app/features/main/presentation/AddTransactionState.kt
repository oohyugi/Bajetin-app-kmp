package com.bajetin.app.features.main.presentation

import kotlinx.serialization.Serializable

@Serializable
data class AddTransactionState(
    val expression: String = "",
    val transactionAmount: String = "0",
)
