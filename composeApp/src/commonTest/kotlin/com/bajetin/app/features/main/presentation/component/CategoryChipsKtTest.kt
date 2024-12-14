package com.bajetin.app.features.main.presentation.component

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import com.bajetin.app.data.entity.TransactionCategoryEntity
import kotlin.test.Test

class CategoryChipsKtTest {

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun `should display category chips`() = runComposeUiTest {
        setContent {
            CategoryChips(
                categories = listOf(
                    TransactionCategoryEntity(
                        emoticon = "\uD83C\uDFEA", label = "Shopping"
                    ),
                    TransactionCategoryEntity(
                        emoticon = "\uD83D\uDE95", label = "Transport"
                    )
                ),
                categorySelected = TransactionCategoryEntity(
                    emoticon = "\uD83C\uDFEA", label = "Shopping"
                ),
                onClickCategory = {}
            )
        }

        onNodeWithText("\uD83C\uDFEA").assertIsDisplayed()
        onNodeWithText("Shopping").assertIsDisplayed()
        onNodeWithText("\uD83D\uDE95").assertIsDisplayed()
        onNodeWithText("Transport").assertIsDisplayed()
    }
}
