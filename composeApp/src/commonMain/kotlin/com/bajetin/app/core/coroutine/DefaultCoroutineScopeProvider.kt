package com.bajetin.app.core.coroutine

import com.bajetin.app.domain.CoroutineScopeProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.plus

class DefaultCoroutineScopeProvider : CoroutineScopeProvider {
    override val externalScope: CoroutineScope = MainScope() + SupervisorJob()
}
