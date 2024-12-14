package com.bajetin.app.features.main.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bajetin.app.core.utils.isOperator
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun NumPad(state: NumpadState, onKeyPress: (NumpadState) -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(60.dp),
        onClick = { onKeyPress(state) },
        elevation = CardDefaults.elevatedCardElevation(0.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                modifier = Modifier,
                text = state.label,
                style = if (state.type.symbol.isOperator() || state.label == "⌫") {
                    MaterialTheme.typography.headlineSmall
                } else {
                    MaterialTheme.typography.titleMedium
                },
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview
@Composable
private fun NumpadPreview() {
    MaterialTheme {
        NumPad(state = NumpadState("1", NumpadType.Number), onKeyPress = {})
    }
}
