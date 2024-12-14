package com.bajetin.app.core.utils

import com.bajetin.app.core.expression.ExpressionParser

fun String.formatToCurrency(): String {
    return reversed()
        .chunked(3)
        .joinToString(".")
        .reversed()
}

fun String.removeDotToInt(): Int? {
    return this.replace(".", "").toIntOrNull()
}

/**
 * @return The evaluated numeric result as a [Double].
 * @throws IllegalArgumentException If the expression is invalid.
 */
fun String.evaluateExpression(): Double {
    val parser = ExpressionParser(this)
    return parser.parse()
}

private val operators = listOf("+", "-", "*", "/")
fun String.containsOperators(): Boolean {
    return operators.any { this.contains(it) }
}

fun String.isOperator(): Boolean {
    return this in operators
}
