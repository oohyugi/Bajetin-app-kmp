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
                TimePeriod.DAY -> {
                    assertEquals(result, Pair(currentDateInMillis, currentDateInMillis))
                }
                // should start from monday 16
                TimePeriod.WEEK -> {
                    assertEquals(result, Pair(1734282000000, currentDateInMillis))
                }
                // should start from sunday 1
                TimePeriod.MONTH -> {
                    assertEquals(result, Pair(1732986000000, currentDateInMillis))
                }
                // should start from monday 1 january 2024
                TimePeriod.YEAR -> {
                    assertEquals(result, Pair(1704042000000, currentDateInMillis))
                }
                // should start from friday, 18 december 2020
                TimePeriod.ALL_TIME -> {
                    assertEquals(result, Pair(1608274131000, currentDateInMillis))
                }
            }
        }
    }
}
