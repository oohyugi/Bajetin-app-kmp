package com.bajetin.app.core.utils

import kotlinx.datetime.Instant
import kotlin.test.Test
import kotlin.test.assertEquals

class DateTimeUtilsKtTest {

    @Test
    fun `calculate time range`() {
        // Wednesday, 18 December 2024
        val currentDateInMillis = 1734504531000

        TimePeriod.entries.forEach {
            val result = calculateTimeRange(
                period = it,
                currentInstant = Instant.fromEpochMilliseconds(currentDateInMillis)
            )

            when (it) {
                // should same with currentTimeInMillis
                TimePeriod.Day -> {
                    assertEquals(result, Pair(currentDateInMillis, currentDateInMillis))
                }
                // should start from monday 16
                TimePeriod.Week -> {
                    assertEquals(result, Pair(1734282000000, currentDateInMillis))
                }
                // should start from sunday 1
                TimePeriod.Month -> {
                    assertEquals(result, Pair(1732986000000, currentDateInMillis))
                }
                // should start from monday 1 january 2024
                TimePeriod.Year -> {
                    assertEquals(result, Pair(1704042000000, currentDateInMillis))
                }
                // should start from friday, 18 december 2020
                TimePeriod.AllTime -> {
                    assertEquals(result, Pair(1608274131000, currentDateInMillis))
                }
            }
        }
    }
}
