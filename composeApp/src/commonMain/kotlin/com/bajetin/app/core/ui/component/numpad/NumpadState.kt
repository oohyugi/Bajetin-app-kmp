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
