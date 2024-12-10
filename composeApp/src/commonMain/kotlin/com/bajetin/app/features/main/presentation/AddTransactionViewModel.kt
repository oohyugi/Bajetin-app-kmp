package com.bajetin.app.features.main.presentation

import androidx.lifecycle.ViewModel
import com.bajetin.app.core.utils.Constants.operators
import com.bajetin.app.data.entity.TransactionCategoryEntity
import com.bajetin.app.data.repository.TransactionCategoryRepo
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
    private val transactionCategoryRepo: TransactionCategoryRepo,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) :
    ViewModel() {

    private var _addTransactionUiState = MutableStateFlow(AddTransactionState())
    val addTransactionUiState = _addTransactionUiState.asStateFlow()

    val categoryUiState: StateFlow<List<TransactionCategoryEntity>> =
        transactionCategoryRepo.getAll().stateIn(
            scope = CoroutineScope(dispatcher),
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    fun onKeyPress(numpadState: NumpadState) {
        when (numpadState.type) {
            NumpadType.Addition,
            NumpadType.Subtraction,
            NumpadType.Multiplication,
            NumpadType.Division -> handleOperatorInput(symbol = numpadState.label)

            NumpadType.Number -> handleNumberInput(numpadState.label)

            NumpadType.Clear -> handleClear()
        }
    }

    fun onClickDone() {
// TODO
    }

    private fun handleOperatorInput(symbol: String) {
        val currentState = _addTransactionUiState.value
        val currentExpression = currentState.expression
        val currentAmount = currentState.transactionAmount

        // Check if the expression ends with an operator
        val tokens = currentExpression.trim().split(" ")
        val lastToken = tokens.lastOrNull()

        if (lastToken != null && isOperator(lastToken)) {
            return
        }

        val newExpression = if (currentExpression.isEmpty()) {
            "$currentAmount $symbol "
        } else {
            "$currentExpression $symbol "
        }
        updateAddTransactionUiState(expression = newExpression, amountStr = currentAmount)
    }

    private fun handleNumberInput(digitStr: String) {
        val maxValue = 999_999_999_999

        val currentState = _addTransactionUiState.value
        val currentExpression = currentState.expression
        val currentAmount = currentState.transactionAmount

        val newAmount = if (currentAmount == "0") {
            digitStr
        } else {
            currentAmount + digitStr
        }

        if ((newAmount.toLongOrNull() ?: 0) > maxValue) {
            updateAddTransactionUiState(
                amountStr = maxValue.toString(),
            )
            return
        }

        val newExpression = if (currentExpression.isEmpty()) {
            newAmount
        } else {
            currentExpression + digitStr
        }

        val result = evaluateExpression(newExpression)

        updateAddTransactionUiState(
            amountStr = result.toString(),
            expression = newExpression
        )
    }

    private fun handleClear() {
        val currentState = _addTransactionUiState.value
        val currentExpression = currentState.expression
        val currentAmount = currentState.transactionAmount

        // clear completely if the expression contains an operator
        if (currentState.isExpressionContainsAnyOperator()) {
            updateAddTransactionUiState(
                amountStr = "0",
                expression = ""
            )
        } else {
            val newAmount = if (currentAmount.isNotEmpty()) currentAmount.dropLast(1) else "0"
            val newExpression =
                if (currentExpression.isNotEmpty()) currentExpression.dropLast(1) else ""

            updateAddTransactionUiState(
                amountStr = newAmount.ifEmpty { "0" },
                expression = newExpression
            )
        }
    }

    private fun updateAddTransactionUiState(amountStr: String? = null, expression: String? = null) {
        val current = _addTransactionUiState.value
        _addTransactionUiState.update { state ->
            state.copy(
                expression = expression ?: current.expression,
                transactionAmount = amountStr ?: current.transactionAmount
            )
        }
    }

    private fun isOperator(token: String): Boolean {
        return token in operators
    }

    private fun evaluateExpression(expression: String): Long {
        val tokens = expression.split(" ")
        var result = tokens[0].toLongOrNull() ?: 0

        var i = 1
        while (i < tokens.size) {
            val op = tokens[i]
            val nextVal = tokens.getOrNull(i + 1)?.toLongOrNull() ?: 0
            result = when (op) {
                "+" -> result + nextVal
                "-" -> result - nextVal
                "×" -> result * nextVal
                "÷" -> if (nextVal != 0L) result / nextVal else 0
                else -> result
            }
            i += 2
        }
        return result
    }
}
