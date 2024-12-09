package com.bajetin.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.bajetin.app.core.ui.theme.BajetinTheme
import com.bajetin.app.di.appModule
import com.bajetin.app.navigation.Navigation
import org.koin.compose.KoinApplication

@Composable
fun App() {

    KoinApplication(
        application = {
            appModule()
        }
    ) {
        BajetinTheme {
            val navHostController = rememberNavController()
            Navigation(navHostController)
        }
    }
}

