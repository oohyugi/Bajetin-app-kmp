package com.bajetin.app.features.transactionHistory.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bajetin.app.core.utils.formatToCurrency
import com.bajetin.app.data.entity.TransactionCategoryEntity
import com.bajetin.app.data.entity.TransactionEntity

@Composable
internal fun TransactionItem(
    category: TransactionCategoryEntity?,
    transaction: TransactionEntity,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier.weight(1F),
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    "${category?.emoticon} ${category?.label}",
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = textColor
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    transaction.notes.orEmpty(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                    color = textColor.copy(alpha = 0.8F)
                )
            }
            Spacer(Modifier.width(16.dp))
            Text(
                "Rp.${transaction.amount.toString().formatToCurrency()}",
                style = MaterialTheme.typography.titleMedium,
                color = textColor
            )
        }
    }
}
