package com.bajetin.app

import androidx.compose.runtime.Composable
import com.bajetin.app.di.coreModule
import com.bajetin.app.di.dataSourceModule
import com.bajetin.app.di.repositoryModule
import com.bajetin.app.di.sqlDriverModule
import com.bajetin.app.di.viewModelModule
import com.bajetin.app.features.main.presentation.MainScreen
import com.bajetin.app.ui.theme.BajetinTheme
import org.koin.compose.KoinApplication
import org.koin.compose.KoinContext

@Composable
fun App() {
    val platform = getPlatform()
    println(platform)
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
                        sqlDriverModule,
                        dataSourceModule,
                        repositoryModule,
                        viewModelModule
                    )
                }
            ) {
                BajetinTheme { MainScreen() }
            }
        }
    }
}
