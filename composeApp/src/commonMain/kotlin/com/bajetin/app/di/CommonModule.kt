package com.bajetin.app.di

import com.bajetin.app.data.local.TransactionCategoryDataSource
import com.bajetin.app.data.local.TransactionCategoryDataSourceImpl
import com.bajetin.app.data.repository.TransactionCategoryRepo
import com.bajetin.app.data.repository.TransactionCategoryRepoImpl
import com.bajetin.app.db.BajetinDatabase
import com.bajetin.app.features.main.presentation.AddTransactionViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

private const val IODispatcher = "IODispatcher"
private const val DefaultDispatcher = "DefaultDispatcher"

val coreModule = module {
    single {
        BajetinDatabase(get())
    }
    single(named(IODispatcher)) {
        Dispatchers.IO
    }
    single(named(DefaultDispatcher)) {
        Dispatchers.Default
    }
}

val dataSourceModule = module {
    single<TransactionCategoryDataSource> {
        TransactionCategoryDataSourceImpl(get(), get(named(IODispatcher)))
    }
}
val repositoryModule = module {
    singleOf(::TransactionCategoryRepoImpl) bind TransactionCategoryRepo::class
}

val viewModelModule = module {
    viewModel {
        AddTransactionViewModel(get(), get(named(DefaultDispatcher)))
    }
}
