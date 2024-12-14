package com.bajetin.app.features.main.presentation

sealed class AddTransactionUiEvent {
    data class ShowSnackbar(val message: String) : AddTransactionUiEvent()
    data object HideSheet : AddTransactionUiEvent()
    data object ShowDatePicker : AddTransactionUiEvent()
}
