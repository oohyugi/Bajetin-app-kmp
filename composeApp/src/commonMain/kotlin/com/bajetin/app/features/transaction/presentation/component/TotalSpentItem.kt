package com.bajetin.app.features.transaction.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bajetin.composeapp.generated.resources.Res
import bajetin.composeapp.generated.resources.spent_title
import com.bajetin.app.core.utils.formatCurrency
import com.bajetin.app.data.entity.TransactionTotalEntity
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun TotalSpentItem(
    totalSpent: TransactionTotalEntity,
    onPeriodClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth().wrapContentSize()
            .padding(bottom = 24.dp)
    ) {
        with(totalSpent) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(stringResource(Res.string.spent_title))
                Spacer(Modifier.size(8.dp))
                Box(
                    modifier = Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = ripple(radius = 16.dp)
                        ) { onPeriodClick() }
                        .background(
                            MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
                            shape = MaterialTheme.shapes.medium,
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)

                ) {
                    Text(
                        timePeriod.name.lowercase()
                            .replaceFirstChar { char ->
                                char.uppercase()
                            },
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
            Spacer(Modifier.size(8.dp))
            Text(
                formatCurrency(totalAmount.toDouble()),
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}
