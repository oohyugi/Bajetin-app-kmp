package com.bajetin.app.features.main.presentation

import androidx.lifecycle.ViewModel
import com.bajetin.app.core.utils.Constants.operators
import com.bajetin.app.core.utils.evaluateExpression
import com.bajetin.app.data.entity.TransactionCategoryEntity
import com.bajetin.app.domain.repository.TransactionRepo
import com.bajetin.app.features.main.presentation.component.NumpadState
import com.bajetin.app.features.main.presentation.component.NumpadType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class AddTransactionViewModel(
    private val transactionRepo: TransactionRepo,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) :
    ViewModel() {

    private var _addTransactionUiState = MutableStateFlow(AddTransactionState())
    val addTransactionUiState = _addTransactionUiState.asStateFlow()

    val categoryUiState: StateFlow<List<TransactionCategoryEntity>> =
        transactionRepo.getAllCategories().stateIn(
            scope = CoroutineScope(dispatcher),
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    fun onKeyPress(numpadState: NumpadState) {
        when (numpadState.type) {
            NumpadType.Addition,
            NumpadType.Subtraction,
            NumpadType.Multiplication,
            NumpadType.Division -> handleOperatorInput(symbol = numpadState.type.symbol)

            NumpadType.Number -> handleNumberInput(numpadState.label)
            NumpadType.Clear -> handleClear()
        }
    }

    fun onClickDone() {
// TODO
    }

    /**
     * Inserts or replaces the operator in the expression.
     * Ensures no consecutive operators and that we don't start with an operator
     * unless there's a number to work with.
     */
    private fun handleOperatorInput(symbol: String) {
        val state = _addTransactionUiState.value
        val expression = state.expression.trim()
        val amount = state.amount
        val tokens = expression.toTokens()
        val lastToken = tokens.lastOrNull()

        when {
            // If there's no expression yet, we must have a number before adding an operator
            lastToken == null && amount.isNotBlank() -> {
                val newExpression = "$amount $symbol "
                updateAddTransactionUiState(
                    expression = newExpression,
                    amountStr = amount
                )
            }

            // If the last token is an operator, replace it with the new one
            lastToken != null && isOperator(lastToken) -> {
                val newExpression = tokens.dropLast(1).joinToString(" ") + " $symbol "
                updateAddTransactionUiState(
                    expression = newExpression,
                    amountStr = amount
                )
            }

            // If the last token is a number, just append the new operator
            lastToken != null && !isOperator(lastToken) -> {
                val newExpression = "$expression $symbol "
                updateAddTransactionUiState(
                    expression = newExpression,
                    amountStr = amount
                )
            }
        }
    }

    /**
     * Appends a number to the current amount and expression.
     * If we start from "0", we replace it with the new digit.
     * Also evaluates the expression to update the displayed amount.
     */
    private fun handleNumberInput(digitStr: String) {
        val state = _addTransactionUiState.value
        val currentExpression = state.expression
        val currentAmount = state.amount

        // Update the amount: if current is "0", replace it, otherwise append.
        val newAmount = if (currentAmount == "0") digitStr else currentAmount + digitStr
        val newExpression = if (currentExpression.isEmpty()) {
            newAmount
        } else {
            currentExpression + digitStr
        }

        val result = newExpression.evaluateExpression()

        updateAddTransactionUiState(
            amountStr = result.toLong().toString(),
            expression = newExpression
        )
    }

    /**
     * Handles the clear action:
     * - If there's an operator in the expression, we clear everything.
     * - Otherwise, we remove one character at a time from amount and expression.
     */
    private fun handleClear() {
        val state = _addTransactionUiState.value
        val currentExpression = state.expression
        val currentAmount = state.amount

        if (state.isExpressionContainsAnyOperator()) {
            // If we have an operator, just reset everything
            updateAddTransactionUiState(
                amountStr = "0",
                expression = ""
            )
        } else {
            // Otherwise, remove last character
            val newAmount = currentAmount.dropLastOrDefault("0")
            val newExpression = currentExpression.dropLastOrDefault("")

            updateAddTransactionUiState(
                amountStr = newAmount,
                expression = newExpression
            )
        }
    }

    /**
     * Updates the UI state with new values for amount and/or expression.
     */
    private fun updateAddTransactionUiState(amountStr: String? = null, expression: String? = null) {
        val current = _addTransactionUiState.value
        _addTransactionUiState.update { state ->
            state.copy(
                expression = expression ?: current.expression,
                amount = amountStr ?: current.amount
            )
        }
    }

    /**
     * Checks whether the given token is considered an operator.
     */
    private fun isOperator(token: String): Boolean {
        return token in operators
    }

    /**
     * Splits an expression string into tokens separated by spaces.
     */
    private fun String.toTokens(): List<String> {
        return trim().split(" ").filter { it.isNotEmpty() }
    }

    /**
     * Drops the last character from a string, or returns the default value if the string is empty.
     */
    private fun String.dropLastOrDefault(defaultValue: String): String {
        return if (isNotEmpty()) dropLast(1).ifEmpty { defaultValue } else defaultValue
    }
}
