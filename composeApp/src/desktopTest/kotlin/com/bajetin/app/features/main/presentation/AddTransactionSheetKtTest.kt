package com.bajetin.app.features.main.presentation

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.testing.TestLifecycleOwner
import com.bajetin.app.core.TestCoroutineDispatcherProvider
import org.junit.Rule
import org.junit.Test

class AddTransactionSheetKtTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun `should display bottom sheet add transaction row`() {
        val testLifecycleOwner = TestLifecycleOwner(Lifecycle.State.RESUMED)
        rule.setContent {
            CompositionLocalProvider(
                value = LocalLifecycleOwner provides testLifecycleOwner
            ) {
                AddTransactionSheet(
                    viewModel = AddTransactionViewModel(
                        transactionRepo = TransactionRepoFake(),
                        TestCoroutineDispatcherProvider()
                    ),

                    onEventLaunch = {},
                )
            }
        }

        rule.onNodeWithText("Rp.").assertIsDisplayed()
        rule.onAllNodes(hasText("0")).onFirst().assertIsDisplayed()
    }
}
