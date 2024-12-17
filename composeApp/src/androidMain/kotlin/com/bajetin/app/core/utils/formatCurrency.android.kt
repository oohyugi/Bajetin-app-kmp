package com.bajetin.app.core.utils

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

actual fun formatCurrency(amount: Double, locale: String): String {
    val loc = Locale.forLanguageTag(locale)
    val formatter = NumberFormat.getCurrencyInstance(loc) as DecimalFormat
    if (locale == "id-ID") {
        formatter.minimumFractionDigits = 0
        formatter.maximumFractionDigits = 0
    }
    return formatter.format(amount)
}
