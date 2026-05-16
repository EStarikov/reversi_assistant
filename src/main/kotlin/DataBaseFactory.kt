import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File

object DataBaseFactory {
    private const val DB_FILE = "reversi.db"

    fun init() {
        val dbFile = File(DB_FILE)
        dbFile.parentFile?.mkdirs()

        Database.connect(
            url = "jdbc:sqlite:$DB_FILE",
            driver = "org.sqlite.JDBC"
        )

        transaction {
            SchemaUtils.create(Players, Games)
        }
    }

    fun getDatabasePath(): String {
        return File(DB_FILE).absolutePath
    }

    fun clearAllData() {
        transaction {
            SchemaUtils.drop(Players, Games)
            SchemaUtils.create(Players, Games)
            println("🗑️ Все данные удалены, таблицы пересозданы")
        }
    }
}