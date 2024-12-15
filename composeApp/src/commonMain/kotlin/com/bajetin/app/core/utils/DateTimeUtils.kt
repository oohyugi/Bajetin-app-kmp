package com.bajetin.app.core.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime

private val DisplayDateWithoutYear: DateTimeFormat<LocalDate> by lazy {
    LocalDate.Format {
        dayOfWeek(DayOfWeekNames.ENGLISH_ABBREVIATED)
        chars(", ")
        dayOfMonth()
        char(' ')
        monthName(MonthNames.ENGLISH_ABBREVIATED)
    }
}
private val DisplayDate: DateTimeFormat<LocalDate> by lazy {
    LocalDate.Format {
        dayOfMonth()
        char(' ')
        monthName(MonthNames.ENGLISH_ABBREVIATED)
        char(' ')
        yearTwoDigits(1970)
    }
}

object DateTimeUtils {
    fun currentDate(): LocalDate {
        return Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault()).date
    }
}

fun Long?.toDisplayDate(): String {
    if (this == null) return TodayTitle
    val instant = Instant.fromEpochMilliseconds(this)
    val localDate = instant.toLocalDateTime(TimeZone.currentSystemDefault()).date
    return if (localDate == DateTimeUtils.currentDate()
    ) {
        TodayTitle
    } else if (localDate.toEpochDays() > DateTimeUtils.currentDate().toEpochDays()) {
        "$AfterCurrentDateTitle ${localDate.format(DisplayDate)}"
    } else if (localDate.year < DateTimeUtils.currentDate().year) {
        localDate.format(DisplayDate)
    } else {
        localDate.format(DisplayDateWithoutYear)
    }
}

@OptIn(FormatStringsInDatetimeFormats::class)
fun Long.toTimeDisplay(): String {
    val instant = Instant.fromEpochMilliseconds(this)
    val localDate = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    val dateFormat = LocalDateTime.Format {
        byUnicodePattern("HH:MM")
    }
    return dateFormat.format(localDate)
}

@OptIn(FormatStringsInDatetimeFormats::class)
fun Long.formatTimestamp(pattern: String = "dd,MM yyyy"): String {
    val instant = Instant.fromEpochMilliseconds(this)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    val dateFormat = LocalDateTime.Format {
        byUnicodePattern(pattern)
    }
    return dateFormat.format(localDateTime)
}

const val TodayTitle = "Today"
const val AfterCurrentDateTitle = "Upcoming"
