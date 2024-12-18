package com.bajetin.app.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>
    data class Success<T>(
        val result: T
    ) : UiState<T>

    data class Error(val message: String) : UiState<Nothing>
}

// Extension function to map flows to UiState
fun <T> Flow<T>.toUiState(): Flow<UiState<T>> = this
    .map { UiState.Success(it) as UiState<T> }
    .onStart { emit(UiState.Loading) }
    .catch { e -> emit(UiState.Error(e.message ?: "An error occurred")) }
