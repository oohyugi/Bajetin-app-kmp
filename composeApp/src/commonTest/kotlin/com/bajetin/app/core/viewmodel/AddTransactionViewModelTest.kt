package com.bajetin.app.core.viewmodel

import app.cash.turbine.test
import com.bajetin.app.features.main.presentation.component.NumpadState
import com.bajetin.app.features.main.presentation.component.NumpadType
import com.bajetin.app.features.main.presentation.AddTransactionViewModel
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class AddTransactionViewModelTest {

    @Test
    fun `onKeyPress with operator appends to expression`() = runTest {
        val viewModel = AddTransactionViewModel()
        viewModel.onKeyPress(NumpadState(type = NumpadType.Number, label = "5"))
        viewModel.onKeyPress(NumpadState(type = NumpadType.Addition, label = "+"))
        viewModel.onKeyPress(NumpadState(type = NumpadType.Number, label = "3"))

        viewModel.addTransactionUiState.test {
            val state = awaitItem()
            assertEquals("5 + 3", state.expression)
            assertEquals("8", state.transactionAmount) // 5 + 3 = 8
        }
    }

    @Test
    fun `handleNumberInput caps value at max`() = runTest {
        val maxValue = 999_999_999_999L
        val number = "9".repeat(13) // Greater than maxValue

        val viewModel = AddTransactionViewModel()
        viewModel.onKeyPress(NumpadState(type = NumpadType.Number, label = number))

        viewModel.addTransactionUiState.test {
            val state = awaitItem()
            assertEquals(maxValue.toString(), state.transactionAmount)
        }
    }

    @Test
    fun `onKeyPress ignores invalid operator`() = runTest {
        val viewModel = AddTransactionViewModel()
        viewModel.onKeyPress(NumpadState(type = NumpadType.Number, label = "5"))
        viewModel.onKeyPress(NumpadState(type = NumpadType.Addition, label = "+"))
        viewModel.onKeyPress(NumpadState(type = NumpadType.Addition, label = "+"))

        viewModel.addTransactionUiState.test {
            val state = awaitItem()
            assertEquals("5 + ", state.expression) // "+" is not appended again
            assertEquals("5", state.transactionAmount)
        }
    }

    @Test
    fun `onKeyPress handles Clear correctly`() = runTest {
        val viewModel = AddTransactionViewModel()
        viewModel.onKeyPress(NumpadState(type = NumpadType.Number, label = "5"))
        viewModel.onKeyPress(NumpadState(type = NumpadType.Clear, label = ""))

        viewModel.addTransactionUiState.test {
            val state = awaitItem()
            assertEquals("", state.expression)
            assertEquals("0", state.transactionAmount)
        }
    }
}
