package com.bajetin.app.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.bajetin.app.db.BajetinDatabase
import org.koin.dsl.module

actual val platformModule = module {
    single { Platform.IOS }

    single<SqlDriver> {
        NativeSqliteDriver(
            schema = BajetinDatabase.Schema,
            name = "bajetin.db"
        )
    }
}
