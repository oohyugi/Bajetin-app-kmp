package com.bajetin.app.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ButtonIcon(
    onClick: () -> Unit,
    label: String,
    leadingIcon: @Composable (Modifier) -> Unit = {},
    modifier: Modifier = Modifier,
    trailingIcon: @Composable (Modifier) -> Unit = {},
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(),
) {
    Button(
        onClick = onClick,
        colors = buttonColors,
        shape = MaterialTheme.shapes.medium,
        contentPadding = PaddingValues(horizontal = 12.dp),
        modifier = modifier,
    ) {
        leadingIcon(Modifier.padding(end = 8.dp))
        Text(label)
        trailingIcon(Modifier.padding(start = 8.dp))
    }
}
