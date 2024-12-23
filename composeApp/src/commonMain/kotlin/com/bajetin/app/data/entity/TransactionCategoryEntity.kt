package com.bajetin.app.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class TransactionCategoryEntity(val id: Long = 0, val emoticon: String?, val label: String) {

    companion object {
        val initialCategories = listOf(
            TransactionCategoryEntity(
                emoticon = "\uD83C\uDF54", // Burger emoji
                label = "Food & Drinks"
            ),
            TransactionCategoryEntity(
                emoticon = "\uD83D\uDE95", // Taxi emoji
                label = "Transport"
            ),
            TransactionCategoryEntity(
                emoticon = "\uD83C\uDFEA", // Shopping bags emoji
                label = "Shopping"
            ),

            TransactionCategoryEntity(
                emoticon = "\uD83C\uDFA6", // Movie Camera
                label = "Entertainment"
            ),
            TransactionCategoryEntity(
                emoticon = "\uD83D\uDCDA", // Books emoji
                label = "Education"
            ),
            TransactionCategoryEntity(
                emoticon = "\uD83D\uDCAA", // Flexed biceps emoji
                label = "Sports"
            ),
            TransactionCategoryEntity(
                emoticon = "\uD83C\uDFE5", // Hospital emoji
                label = "Health"
            ),
            TransactionCategoryEntity(
                emoticon = "\uD83D\uDCC5", // Calendar
                label = "Subscriptions"
            ),
            TransactionCategoryEntity(
                emoticon = "\uD83D\uDCB8", // Money with wings emoji
                label = "Business"
            ),
            TransactionCategoryEntity(
                emoticon = "\uD83D\uDC85", // Nail polish emoji
                label = "Personal Care"
            ),
            TransactionCategoryEntity(
                emoticon = "\uD83C\uDFE0", // House emoji
                label = "Household"
            ),
            TransactionCategoryEntity(
                emoticon = "✏\uFE0F", // Pencil emoji
                label = "Other"
            ),
        )
    }
}
