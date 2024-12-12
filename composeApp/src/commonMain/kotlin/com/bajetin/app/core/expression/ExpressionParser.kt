package com.bajetin.app.core.expression
/**
 * A simple recursive-descent expression parser for basic arithmetic expressions.
 *
 * This class can parse and evaluate arithmetic expressions containing:
 * - Addition (`+`)
 * - Subtraction (`-`)
 * - Multiplication (`*`)
 * - Division (`/`)
 *
 * The grammar:
 * ```
 * Expression -> Term { ('+' | '-') Term }
 * Term       -> Factor { ('*' | '/') Factor }
 * Factor     -> Number
 * ```
 *
 * The parser does not handle:
 * - Parentheses
 * - Unary operators (e.g., `-5` as a standalone factor)
 *
 * Example:
 * ```
 * val parser = ExpressionParser("3 + 5 * 10 - 2 / 4")
 * val result = parser.parse() // result = 52.5
 * ```
 *
 * @property expression The arithmetic expression to be parsed and evaluated.
 * @throws IllegalArgumentException If the expression contains invalid characters or malformed numbers.
 */
internal class ExpressionParser(private val expression: String) {

    private val tokens: List<String> = tokenize(expression)
    private var currentPosition = 0

    /**
     * Parses the entire expression and returns the computed value.
     *
     * @return The result of evaluating the expression as a [Double].
     * @throws IllegalArgumentException If there are unexpected tokens or the expression is invalid.
     */
    fun parse(): Double {
        currentPosition = 0
        val value = parseExpression()
        require(currentPosition == tokens.size) {
            "Unexpected token at the end: ${currentToken()}"
        }

        return value
    }

    /**
     * Splits the input expression into a list of tokens (numbers and operators).
     *
     * Tokens are:
     * - Numbers (sequences of digits)
     * - Operators: `+`, `-`, `*`, `/`
     * - Whitespace is ignored.
     *
     * @param expression The string to tokenize.
     * @return A list of token strings.
     * @throws IllegalArgumentException If an invalid number or unexpected character is encountered.
     */
    private fun tokenize(expression: String): List<String> {
        val result = mutableListOf<String>()
        var index = 0

        while (index < expression.length) {
            val char = expression[index]
            when {
                char.isWhitespace() -> {
                    index++
                }
                char in listOf('+', '-', '*', '/') -> {
                    result.add(char.toString())
                    index++
                }
                char.isDigit() -> {
                    val start = index
                    while (index < expression.length && expression[index].isDigit()) {
                        index++
                    }
                    val numberToken = expression.substring(start, index)
                    require(numberToken.toDoubleOrNull() != null) {
                        "Invalid number: $numberToken"
                    }
                    result.add(numberToken)
                }
                else -> {
                    throw IllegalArgumentException("Unexpected character: $char")
                }
            }
        }

        return result
    }

    /**
     * Returns the current token or `null` if no more tokens are available.
     */
    private fun currentToken(): String? = tokens.getOrNull(currentPosition)

    /**
     * Advances to the next token.
     */
    private fun advanceToken() {
        currentPosition++
    }

    /**
     * Parses and evaluates an expression:
     * Expression -> Term { ('+' | '-') Term }
     */
    private fun parseExpression(): Double {
        var value = parseTerm()
        while (currentToken() == "+" || currentToken() == "-") {
            val op = currentToken()!!
            advanceToken()
            val rightValue = parseTerm()
            value = when (op) {
                "+" -> value + rightValue
                "-" -> value - rightValue
                else -> value
            }
        }
        return value
    }

    /**
     * Parses and evaluates a term:
     * Term -> Factor { ('*' | '/') Factor }
     */
    private fun parseTerm(): Double {
        var value = parseFactor()
        while (currentToken() == "*" || currentToken() == "/") {
            val op = currentToken()!!
            advanceToken()
            val rightValue = parseFactor()
            value = when (op) {
                "*" -> value * rightValue
                "/" -> {
                    require(rightValue != 0.0) { "Division by zero" }
                    value / rightValue
                }
                else -> value
            }
        }
        return value
    }

    /**
     * Parses and returns a factor:
     * Factor -> Number
     */
    private fun parseFactor(): Double {
        val token = currentToken()
            ?: throw IllegalArgumentException("Number expected at position $currentPosition")

        val number = token.toDoubleOrNull()
            ?: throw IllegalArgumentException("Invalid number: $token")

        advanceToken()
        return number
    }
}
