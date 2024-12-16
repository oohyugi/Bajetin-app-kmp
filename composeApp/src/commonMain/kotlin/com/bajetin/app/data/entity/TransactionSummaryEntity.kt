package com.bajetin.app.data.entity

import com.bajetin.app.core.utils.TimePeriod
import kotlinx.serialization.Serializable

@Serializable
data class TransactionSummaryEntity(
    val category: TransactionCategoryEntity,
    val totalAmount: Long,
    val timePeriod: TimePeriod,
)
