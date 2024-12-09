package com.bajetin.app.core.ui.component.numpad

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun NumPad(state: NumpadState, onKeyPress: (NumpadState) -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(60.dp),
        onClick = {onKeyPress(state)},
        elevation = CardDefaults.elevatedCardElevation(0.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        ) {
        Text(
            modifier = Modifier.fillMaxSize().wrapContentSize(),
            text = state.label,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.W400),
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
private fun NumpadPreview() {
    MaterialTheme {
        NumPad(state = NumpadState("1", NumpadType.Number), onKeyPress = {})
    }
}
