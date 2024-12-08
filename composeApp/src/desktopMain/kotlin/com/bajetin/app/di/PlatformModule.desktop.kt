package com.bajetin.app.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.bajetin.app.db.BajetinDatabase
import org.koin.dsl.module

actual val platformModule = module {
    single<SqlDriver> {
        val driver = JdbcSqliteDriver(
            url = "jdbc:sqlite:bajetin.db"
        )
        BajetinDatabase.Schema.create(driver)
        driver
    }
}
