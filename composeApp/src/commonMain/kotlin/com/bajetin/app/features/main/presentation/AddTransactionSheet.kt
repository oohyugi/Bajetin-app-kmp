package com.bajetin.app.features.main.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bajetin.app.features.main.presentation.component.NumpadRow
import com.bajetin.app.core.utils.formatNumberWithDot

@Composable
fun AddTransactionSheet(
    modifier: Modifier = Modifier,
    viewModel: AddTransactionViewModel,
) {
    val addTransactionUiState = viewModel.addTransactionUiState.collectAsStateWithLifecycle().value

    val categoryUiState = viewModel.categoryUiState.collectAsStateWithLifecycle().value
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        val expression = remember(addTransactionUiState.expression) {
            if (addTransactionUiState.isExpressionContainsAnyOperator()) addTransactionUiState.expression else ""
        }

        Text(expression, style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center)

        categoryUiState.forEach {
            Text("${it.emoticon} ${it.label}")
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text("Rp.")
            Text(
                addTransactionUiState.transactionAmount.formatNumberWithDot(),
                style = MaterialTheme.typography.displayMedium,
                maxLines = 1,
                overflow = TextOverflow.Visible
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        NumpadRow(
            onKeyPress = viewModel::onKeyPress,
            onClickDone = viewModel::onClickDone,
            modifier = Modifier.padding(16.dp)
        )
    }
}

