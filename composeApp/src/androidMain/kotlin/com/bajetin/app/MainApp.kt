package com.bajetin.app

import android.app.Application
import com.bajetin.app.di.coreModule
import com.bajetin.app.di.dataSourceModule
import com.bajetin.app.di.platformModule
import com.bajetin.app.di.repositoryModule
import com.bajetin.app.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApp)
            modules(platformModule, dataSourceModule, coreModule, repositoryModule, viewModelModule)
        }
    }
}
