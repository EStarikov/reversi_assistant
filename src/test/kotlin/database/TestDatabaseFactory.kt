package database

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object TestDatabaseFactory {
    fun init() {

        Database.connect(
            url = "jdbc:sqlite::memory",
            driver = "org.sqlite.JDBC"
        )

        transaction {
            SchemaUtils.create(Players, Games)
        }
    }

    fun clearAndRecreate() {
        transaction {
            SchemaUtils.drop(Players, Games)
            SchemaUtils.create(Players, Games)
        }
    }
}