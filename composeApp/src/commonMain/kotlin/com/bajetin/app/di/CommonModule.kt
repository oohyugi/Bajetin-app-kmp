package com.bajetin.app.di

import com.bajetin.app.core.viewmodel.TransactionViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::TransactionViewModel)
}
