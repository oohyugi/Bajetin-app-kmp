package com.bajetin.app.core.ui.component.numpad

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bajetin.app.core.ui.theme.BajetinTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NumPad(state: NumpadState, onKeyPress: (NumpadState) -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(60.dp), onClick = { onKeyPress(state) },
        elevation = 0.dp,
        shape = RoundedCornerShape(14.dp),
        backgroundColor = BajetinTheme.colors.backgroundDefault,

    ) {
        Text(
            modifier = Modifier.fillMaxSize().wrapContentSize(),
            text = state.label,
            style = BajetinTheme.typography.headingMedium.copy(fontWeight = FontWeight.W400),
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