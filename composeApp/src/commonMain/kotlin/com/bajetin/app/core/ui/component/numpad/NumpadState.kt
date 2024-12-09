package com.bajetin.app.core.ui.component.numpad

enum class NumpadType {
    Addition,
    Subtraction,
    Multiplication,
    Division,
    Number,
    Clear,
}

class NumpadState(
    val label: String,
    val type: NumpadType,
)

val operators = listOf("+", "-", "×", "÷")

val numpadsState = listOf(
    listOf(
        NumpadState("⌫", type = NumpadType.Clear),
        NumpadState("7", type = NumpadType.Number),
        NumpadState("4", type = NumpadType.Number),
        NumpadState("1", type = NumpadType.Number),
        NumpadState("0", type = NumpadType.Number)
    ),
    listOf(
        NumpadState("×", type = NumpadType.Multiplication),
        NumpadState("8", type = NumpadType.Number),
        NumpadState("5", type = NumpadType.Number),
        NumpadState("2", type = NumpadType.Number),
        NumpadState("000", type = NumpadType.Number)
    ),
    listOf(
        NumpadState("÷", type = NumpadType.Division),
        NumpadState("9", type = NumpadType.Number),
        NumpadState("6", type = NumpadType.Number),
        NumpadState("3", type = NumpadType.Number)
    ),
    listOf(
        NumpadState("-", type = NumpadType.Subtraction),
        NumpadState("+", type = NumpadType.Addition)
    )
)