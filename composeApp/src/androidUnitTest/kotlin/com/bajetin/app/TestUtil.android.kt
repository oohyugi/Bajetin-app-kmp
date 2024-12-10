package com.bajetin.app

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.bajetin.app.db.BajetinDatabase

import org.koin.dsl.module

actual val testModule = module {
    single<SqlDriver> {
        JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
            .also { BajetinDatabase.Schema.create(it) }
    }
}
