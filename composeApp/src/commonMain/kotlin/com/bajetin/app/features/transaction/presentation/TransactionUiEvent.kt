package com.bajetin.app.features.transaction.presentation

import com.bajetin.app.data.entity.TransactionEntity

sealed class TransactionUiEvent {
    data class Clicked(val transaction: TransactionEntity) : TransactionUiEvent()
}
