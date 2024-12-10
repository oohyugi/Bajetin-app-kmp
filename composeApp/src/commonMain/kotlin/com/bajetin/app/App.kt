package com.bajetin.app

import androidx.compose.runtime.Composable
import com.bajetin.app.di.Platform
import com.bajetin.app.di.coreModule
import com.bajetin.app.di.dataSourceModule
import com.bajetin.app.di.platformModule
import com.bajetin.app.di.repositoryModule
import com.bajetin.app.di.viewModelModule
import com.bajetin.app.features.main.presentation.MainScreen
import com.bajetin.app.ui.theme.BajetinTheme
import org.koin.compose.KoinApplication
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

@Composable
fun App() {
    val platform: Platform = koinInject()
    when (platform) {
        Platform.Android -> {
            KoinContext {
                BajetinTheme { MainScreen() }
            }
        }

        Platform.Desktop, Platform.IOS, Platform.Unknown -> {
            KoinApplication(
                application = {
                    modules(
                        coreModule,
                        dataSourceModule,
                        repositoryModule,
                        platformModule,
                        viewModelModule
                    )
                }
            ) {
                BajetinTheme { MainScreen() }
            }
        }
    }
}
