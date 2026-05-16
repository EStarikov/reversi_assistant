import DataBaseFactory
import Games
import Players
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction

object TestDataBase {
    private var initialized = false

    fun withTestDataBase(block: () -> Unit) {
        Database.connect(
            url = "jdbc:sqlite::memory:",
            driver = "org.sqlite.JDBC"
        )

        transaction {
            SchemaUtils.create(Players, Games)
            block()
        }
    }

    fun clearDataBase() {
        transaction {
            Games.deleteAll()
            Players.deleteAll()
        }
    }
}