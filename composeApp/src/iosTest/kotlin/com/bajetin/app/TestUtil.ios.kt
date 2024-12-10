package com.bajetin.app

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.inMemoryDriver
import com.bajetin.app.db.BajetinDatabase
import org.koin.dsl.module

actual val testModule = module {
    single<SqlDriver> {
        // In-memory database
        inMemoryDriver(BajetinDatabase.Schema)
    }
}
