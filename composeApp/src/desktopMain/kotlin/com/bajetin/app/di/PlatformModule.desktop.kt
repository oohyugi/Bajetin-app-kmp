package com.bajetin.app.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.bajetin.app.db.BajetinDatabase
import org.koin.dsl.module
import java.sql.SQLDataException

actual val platformModule = module {
    single<Platform> { Platform.Desktop }
    single<SqlDriver> {
        val driver = JdbcSqliteDriver(
            url = "jdbc:sqlite:bajetin.db"
        )
        try {
            BajetinDatabase.Schema.create(driver)
        } catch (e: SQLDataException) {
            if (!e.message.orEmpty().contains("already exists")) {
                throw e
            }
        }
        driver
    }
}
