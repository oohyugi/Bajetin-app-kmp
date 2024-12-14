package com.bajetin.app.ui.component

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
    leadingIcon: @Composable () -> Unit = {},
    modifier: Modifier = Modifier,
    trailingIcon: @Composable () -> Unit = {},
    buttonColors: ButtonColors = ButtonDefaults.buttonColors()
) {
    Button(
        onClick = onClick,
        colors = buttonColors,
        shape = MaterialTheme.shapes.medium,
        modifier = modifier,
    ) {
        leadingIcon()
        Text(label, modifier = Modifier.padding(horizontal = 4.dp))
        trailingIcon()
    }
}
