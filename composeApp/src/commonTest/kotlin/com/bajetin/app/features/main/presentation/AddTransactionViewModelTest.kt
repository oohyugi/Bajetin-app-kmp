package com.bajetin.app.features.main.presentation

import app.cash.turbine.test
import com.bajetin.app.data.entity.TransactionCategoryEntity
import com.bajetin.app.domain.repository.TransactionRepo
import com.bajetin.app.features.main.presentation.component.NumpadState
import com.bajetin.app.features.main.presentation.component.NumpadType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.koin.test.KoinTest
import kotlin.test.Test
import kotlin.test.assertEquals

class AddTransactionViewModelTest : KoinTest {

    private val viewModel =
        AddTransactionViewModel(transactionRepo = TransactionRepoFake())

    @Test
    fun `onKeyPress with operator appends to expression`() = runTest {
        viewModel.onKeyPress(NumpadState(type = NumpadType.Number, label = "5"))
        viewModel.onKeyPress(NumpadState(type = NumpadType.Addition, label = "+"))
        viewModel.onKeyPress(NumpadState(type = NumpadType.Number, label = "3"))

        viewModel.addTransactionUiState.test {
            val state = awaitItem()
            assertEquals("5 + 3", state.expression)
            assertEquals("8", state.amount) // 5 + 3 = 8
        }
    }

    @Test
    fun `onKeyPress ignores invalid operator`() = runTest {
        viewModel.onKeyPress(NumpadState(type = NumpadType.Number, label = "5"))
        viewModel.onKeyPress(NumpadState(type = NumpadType.Addition, label = "+"))
        viewModel.onKeyPress(NumpadState(type = NumpadType.Addition, label = "+"))

        viewModel.addTransactionUiState.test {
            val state = awaitItem()
            assertEquals("5 + ", state.expression) // "+" is not appended again
            assertEquals("5", state.amount)
        }
    }

    @Test
    fun `onKeyPress handles Clear correctly`() = runTest {
        viewModel.onKeyPress(NumpadState(type = NumpadType.Number, label = "5"))
        viewModel.onKeyPress(NumpadState(type = NumpadType.Clear, label = ""))

        viewModel.addTransactionUiState.test {
            val state = awaitItem()
            assertEquals("", state.expression)
            assertEquals("0", state.amount)
        }
    }

    @Test
    fun `should return default categories`() = runTest {
        val viewModel = AddTransactionViewModel(
            TransactionRepoFake(
                categories = TransactionCategoryEntity.initialCategories,
            ),
        )

        viewModel.categoryUiState.test {
            assertEquals(emptyList(), awaitItem())
            assertEquals(
                TransactionCategoryEntity.initialCategories,
                awaitItem()
            )
        }
    }
}

class TransactionRepoFake(
    val insertCategoryFake: ((String, String?) -> Unit)? = null,
    val categories: List<TransactionCategoryEntity> = emptyList()
) : TransactionRepo {
    private val categoryFlow = MutableStateFlow(categories)

    override suspend fun insertCategory(label: String, emoticon: String?) {
        insertCategoryFake?.invoke(label, emoticon)
    }

    override fun getAllCategories(): Flow<List<TransactionCategoryEntity>> = categoryFlow
}
