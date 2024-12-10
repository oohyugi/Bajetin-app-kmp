package com.bajetin.app.core.ui.component

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.bajetin.app.features.main.presentation.component.NumpadRow
import org.junit.Rule
import kotlin.test.Test

class NumpadRowTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun `should display numpad row`() {
        rule.setContent {
            NumpadRow(onKeyPress = {}, onClickDone = {})
        }

        List(9) {
            rule.onNodeWithText("${it + 1}").assertIsDisplayed()
        }
        rule.onNodeWithText("+").assertIsDisplayed()
        rule.onNodeWithText("-").assertIsDisplayed()
        rule.onNodeWithText("⌫").assertIsDisplayed()
        rule.onNodeWithText("÷").assertIsDisplayed()
        rule.onNodeWithText("000").assertIsDisplayed()
        rule.onNodeWithContentDescription("done").assertIsDisplayed()
    }
}
