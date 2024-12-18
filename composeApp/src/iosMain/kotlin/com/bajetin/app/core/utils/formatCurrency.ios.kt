package com.bajetin.app.core.utils

import platform.Foundation.NSLocale
import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterCurrencyStyle

actual fun formatCurrency(amount: Double, locale: String): String {
    val nsLocale = NSLocale(locale)
    val formatter = NSNumberFormatter().apply {
        this.locale = nsLocale
        this.numberStyle = NSNumberFormatterCurrencyStyle
        if (locale == "id-ID") this.minimumFractionDigits = 0u
    }
    val number = NSNumber(amount)
    return formatter.stringFromNumber(number) ?: "$amount"
}
