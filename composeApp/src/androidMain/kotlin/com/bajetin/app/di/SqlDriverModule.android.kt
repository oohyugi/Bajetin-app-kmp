package com.bajetin.app.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.bajetin.app.db.BajetinDatabase
import org.koin.dsl.module

actual val sqlDriverModule = module {
    single<SqlDriver> {
        AndroidSqliteDriver(
            schema = BajetinDatabase.Schema,
            context = get(),
            name = "bajetin.db"
        )
    }
}
