package com.bajetin.app.core.utils

import java.text.NumberFormat
import java.util.Locale

actual fun formatCurrency(amount: Double, locale: String): String {
    val loc = Locale.forLanguageTag(locale)
    val formatter = NumberFormat.getCurrencyInstance(loc)
    if (locale == "id-Id") formatter.minimumFractionDigits = 0
    return formatter.format(amount)
}
