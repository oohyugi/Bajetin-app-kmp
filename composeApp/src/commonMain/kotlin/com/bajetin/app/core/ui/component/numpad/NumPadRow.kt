package com.bajetin.app.core.ui.component.numpad

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun NumpadRow(
    onKeyPress: (NumpadState) -> Unit,
    onClickDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        numpadsState.dropLast(1).forEach { row ->
            NumpadColumn(
                rowStates = row,
                onKeyPress = onKeyPress,
                modifier = Modifier.weight(1f)
            )
        }

        LastColumn(
            rowStates = numpadsState.last(),
            onKeyPress = onKeyPress,
            onClickDone = onClickDone,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun NumpadColumn(
    rowStates: List<NumpadState>,
    onKeyPress: (NumpadState) -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        rowStates.forEach { state ->
            NumPad(
                state = state,
                onKeyPress = onKeyPress,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            )
        }
    }
}

@Composable
fun LastColumn(
    rowStates: List<NumpadState>,
    onKeyPress: (NumpadState) -> Unit,
    onClickDone: () -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        rowStates.forEach { state ->
            NumPad(
                state = state,
                onKeyPress = onKeyPress,
                modifier = Modifier.fillMaxWidth().padding(4.dp)
            )
        }
        DoneButton(onClickDone, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
private fun DoneButton(onClickDone: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        onClick = onClickDone,
        modifier = modifier.height((60 * 3).dp).padding(4.dp), // 60 is numpad height
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Icon(
                Icons.Default.Done,
                "done",
                tint = Color.White,
            )
        }
    }
}

@Preview
@Composable
private fun NumpadRowPreview() {
    MaterialTheme {
        Scaffold {
            NumpadRow(
                onKeyPress = {},
                onClickDone = {},
                modifier = Modifier.fillMaxSize().wrapContentHeight().padding(16.dp)
            )
        }
    }
}
