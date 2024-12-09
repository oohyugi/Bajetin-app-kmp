package com.bajetin.app.features.transaction.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bajetin.app.core.ui.component.numpad.NumpadRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Transaction") }
            )
        }
    ) {
        NumpadRow(
            onKeyPress = {},
            onClickDone = {},
            modifier = Modifier.fillMaxSize().wrapContentHeight().padding(16.dp)
        )
    }
}
