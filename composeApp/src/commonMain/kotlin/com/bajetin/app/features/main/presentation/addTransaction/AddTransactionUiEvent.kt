package com.bajetin.app.features.main.presentation.addTransaction

sealed class AddTransactionUiEvent {
    data object HideSheet : AddTransactionUiEvent()
    data object ShowDatePicker : AddTransactionUiEvent()
    data object ExpandCategory : AddTransactionUiEvent()
}
