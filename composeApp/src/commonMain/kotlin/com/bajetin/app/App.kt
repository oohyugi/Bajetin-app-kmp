package com.bajetin.app

import androidx.compose.runtime.Composable
import com.bajetin.app.di.platformModule
import com.bajetin.app.di.viewModelModule
import com.bajetin.app.features.main.presentation.MainScreen
import com.bajetin.app.ui.theme.BajetinTheme
import org.koin.compose.KoinApplication

@Composable
fun App() {
    KoinApplication(
        application = {
            modules(platformModule, viewModelModule)
        }
    ) {
        BajetinTheme { MainScreen() }
    }
}
