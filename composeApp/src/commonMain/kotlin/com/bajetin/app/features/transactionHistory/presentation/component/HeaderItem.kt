package com.bajetin.app.features.transactionHistory.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import bajetin.composeapp.generated.resources.Res
import bajetin.composeapp.generated.resources.ic_clock_square
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun HeaderItem(date: String?, textColor: Color, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        if (date?.contains("upcoming", ignoreCase = true) == true) {
            Icon(
                painter = painterResource(Res.drawable.ic_clock_square),
                "up coming",
                tint = textColor,
                modifier = Modifier.size(16.dp)
            )
        }
        Text(
            date ?: "",
            style = MaterialTheme.typography.labelLarge,
            color = textColor,
        )
    }
}
