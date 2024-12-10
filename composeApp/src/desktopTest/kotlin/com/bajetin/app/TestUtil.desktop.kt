package com.bajetin.app

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.bajetin.app.db.BajetinDatabase
import org.koin.dsl.module

actual val testModule = module {
    single<SqlDriver> {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        BajetinDatabase.Schema.create(driver)
        driver
    }
}
