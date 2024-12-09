package com.bajetin.app.utils

import com.bajetin.app.core.ui.component.numpad.operators

fun String.formatNumberWithDot(): String {
    return reversed()
        .chunked(3)
        .joinToString(".")
        .reversed()
}

fun String.removeDotToInt(): Int? {
    return this.replace(".", "").toIntOrNull()
}

fun String.containsAnyOperator() = operators.any { this.contains(it) }
