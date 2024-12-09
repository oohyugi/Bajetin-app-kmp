package com.bajetin.app.features.transaction.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bajetin.app.core.ui.component.numpad.NumpadRow

@Composable
fun AddTransactionSheet(modifier: Modifier = Modifier, value: String = "0") {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Rp.")
            Text(value, style = MaterialTheme.typography.displayMedium)
        }
        Spacer(modifier = Modifier.height(24.dp))
        NumpadRow(
            onKeyPress = {},
            onClickDone = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
