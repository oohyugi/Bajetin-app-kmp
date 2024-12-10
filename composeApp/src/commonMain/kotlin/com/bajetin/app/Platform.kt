package com.bajetin.app

sealed class Platform {
    data object Android : Platform()
    data object IOS : Platform()
    data object Desktop : Platform()
    data object Unknown : Platform()
}

expect fun getPlatform(): Platform
