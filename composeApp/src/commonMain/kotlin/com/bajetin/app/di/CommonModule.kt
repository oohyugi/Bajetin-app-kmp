package com.bajetin.app.di

import com.bajetin.app.features.main.presentation.AddTransactionViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::AddTransactionViewModel)
}
