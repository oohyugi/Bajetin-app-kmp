package com.bajetin.app.data.entity

import kotlinx.serialization.Serializable
import com.bajetin.app.core.utils.TimePeriod

@Serializable
data class TransactionTotalEntity(
    val totalAmount: Long,
    val timePeriod: TimePeriod,
)
