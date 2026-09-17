package database

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import player.Player

object Players : IntIdTable("players") {
    val name = varchar("name", 255)
    val elo = double("elo").default(1000.0)
    val registeredAt = datetime("registered_at").defaultExpression(CurrentDateTime)
}

fun ResultRow.toPlayerEntity(): Player {
    return Player(
        name = this[Players.name],
        elo = this[Players.elo],
        registeredAt = this[Players.registeredAt]
    )
}

fun Player.toPlayersInsertMap(): Map<Column<*>, Any?> = mapOf(
    Players.name to this.getName(),
    Players.elo to this.getElo()
)
