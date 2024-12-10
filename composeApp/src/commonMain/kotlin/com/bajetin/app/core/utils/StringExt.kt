package com.bajetin.app.core.utils

fun String.formatNumberWithDot(): String {
    return reversed()
        .chunked(3)
        .joinToString(".")
        .reversed()
}

fun String.removeDotToInt(): Int? {
    return this.replace(".", "").toIntOrNull()
}
