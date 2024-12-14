package com.bajetin.app.features.main.presentation

import app.cash.turbine.test
import com.bajetin.app.data.entity.TransactionCategoryEntity
import com.bajetin.app.domain.repository.TransactionRepo
import com.bajetin.app.features.main.presentation.component.NumpadState
import com.bajetin.app.features.main.presentation.component.NumpadType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.koin.test.KoinTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
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

    @Test
    fun `onClickSave inserts transaction when state is valid`() = runTest {
        var isInsert = false
        val viewModel = AddTransactionViewModel(
            TransactionRepoFake(
                categories = TransactionCategoryEntity.initialCategories,
                insertTransactionFake = { _, _, _, _ ->
                    isInsert = true
                }
            ),
        )
        val category = TransactionCategoryEntity(label = "Food", emoticon = "🍔")
        viewModel.selectCategory(category)
        viewModel.onKeyPress(NumpadState("1", NumpadType.Number))
        viewModel.onKeyPress(NumpadState("0", NumpadType.Number))
        viewModel.onSelectedDate(171434)
        viewModel.addNotes("Beli ayam geprek")

        viewModel.onClickSave()
        advanceUntilIdle()

        assertEquals(isInsert, true)
    }

    @Test
    fun `onSelectedDate updates dateMillis correctly`() = runTest {
        val selectedDate = 1625072400000L

        viewModel.onSelectedDate(selectedDate)

        val uiState = viewModel.addTransactionUiState.value
        assertEquals(uiState.dateMillis, selectedDate)
    }

    @Test
    fun `selectCategory updates categorySelected correctly`() = runTest {
        val category = TransactionCategoryEntity(label = "Food", emoticon = "🍔")

        viewModel.selectCategory(category)

        val uiState = viewModel.addTransactionUiState.value
        assertEquals(uiState.categorySelected, category)
    }

    @Test
    fun `addNotes updates notes correctly`() = runTest {
        val notes = "Beli garam"

        viewModel.addNotes(notes)

        val uiState = viewModel.addTransactionUiState.value
        assertEquals(uiState.notes, notes)
    }
}

class TransactionRepoFake(
    val insertCategoryFake: ((String, String?) -> Unit)? = null,
    val categories: List<TransactionCategoryEntity> = emptyList(),
    val insertTransactionFake: ((catId: Long?, amount: String, dateMillis: Long?, notes: String) -> Unit)? = null,
) : TransactionRepo {
    private val categoryFlow = MutableStateFlow(categories)

    override suspend fun insertCategory(label: String, emoticon: String?) {
        insertCategoryFake?.invoke(label, emoticon)
    }

    override fun getAllCategories(): Flow<List<TransactionCategoryEntity>> = categoryFlow
    override suspend fun insertTransaction(
        catId: Long?,
        amount: String,
        dateMillis: Long?,
        notes: String
    ) {
        insertTransactionFake?.invoke(catId, amount, dateMillis, notes)
    }
}
