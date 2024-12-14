package com.bajetin.app.core

import com.bajetin.app.domain.CoroutineDispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@OptIn(ExperimentalCoroutinesApi::class)
class TestCoroutineDispatcherProvider(
    override val main: CoroutineDispatcher = UnconfinedTestDispatcher(),
    override val io: CoroutineDispatcher = UnconfinedTestDispatcher(),
    override val default: CoroutineDispatcher = UnconfinedTestDispatcher()
) : CoroutineDispatcherProvider
