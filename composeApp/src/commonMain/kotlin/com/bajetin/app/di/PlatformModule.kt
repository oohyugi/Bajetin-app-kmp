package com.bajetin.app.di

import org.koin.core.module.Module

expect val platformModule: Module

sealed class Platform {
    data object Android : Platform()
    data object IOS : Platform()
    data object Desktop : Platform()
    data object Unknown : Platform()
}
