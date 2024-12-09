package com.bajetin.app

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bajetin.app.core.ui.component.numpad.NumpadRow
import com.bajetin.app.core.ui.theme.BajetinTheme
import com.bajetin.app.di.appModule
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

@OptIn(ExperimentalLayoutApi::class)
@Composable
@Preview
fun App() {

    KoinApplication(
        application = {
            appModule()
        }
    ) {
        BajetinTheme {

            Scaffold(backgroundColor = BajetinTheme.colors.backgroundDefault) {
                NumpadRow(
                    onKeyPress = {},
                    onClickDone = {},
                    modifier = Modifier.fillMaxSize().wrapContentHeight().padding(16.dp)
                )
            }
        }
    }
}

