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
import com.bajetin.app.data.entity.TransactionCategoryEntity
import com.bajetin.app.domain.repository.TransactionRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
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
                        transactionRepo = object :
                            TransactionRepo {
                            override suspend fun insertCategory(label: String, emoticon: String?) =
                                Unit

                            override fun getAllCategories(): Flow<List<TransactionCategoryEntity>> {
                                return flowOf()
                            }

                            override suspend fun insertTransaction(
                                catId: Long?,
                                amount: String,
                                dateMillis: Long?,
                                notes: String
                            ) = Unit
                        },
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
