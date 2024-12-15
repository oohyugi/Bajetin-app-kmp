package com.bajetin.app.domain

import kotlinx.coroutines.CoroutineScope

interface CoroutineScopeProvider {
    val externalScope: CoroutineScope
}
