import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.javatime.CurrentDateTime

object Players : Table("players") {
    val name = varchar("name", 255)
    val elo = integer("elo").default(1000)
    val registeredAt = datetime("registered_at").defaultExpression(CurrentDateTime)

    override val primaryKey = PrimaryKey(name)
}

object Games : IntIdTable("games") {
    val player1Name = varchar("player1_name", 255).references(Players.name)
    val player2Name = varchar("player2_name", 255).references(Players.name).nullable()
    val winnerName = varchar("winner_name", 255).nullable()
    val score = varchar("score", 20)
    val moves = text("moves")
    val playedAt = datetime("played_at").defaultExpression(CurrentDateTime)
}