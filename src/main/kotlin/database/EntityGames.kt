package database

import Game
import board.Position
import board.PositionInProgram
import database.Games.moves
import database.Games.rulesetName
import database.Games.size
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime
import rules.AntiReversiRules
import rules.NxNReversiRules
import rules.OthelloRules

object Games : IntIdTable("games") {
    val player1Name = varchar("player1_name", 255).references(Players.name)
    val player2Name = varchar("player2_name", 255).references(Players.name)
    val winnerName = varchar("winner_name", 255).references(Players.name).nullable()
    val rulesetName = varchar("ruleset_name", 255)
    val size = integer("size")
    val score = varchar("score", 20)
    val moves = text("moves")
    val playedAt = datetime("played_at").defaultExpression(CurrentDateTime)
}

fun ResultRow.toGameEntity(): Game {
    val moves = this[moves].split(" ").toTypedArray()
    val startPosition = Array(4) { index ->
        Pair(Position(index, index), index)
    }
    for (i in 0..3) {
        startPosition[i] = Pair(PositionInProgram().makeProgram(moves[i]), (i + 1) % 2)
    }
    var ruleset = NxNReversiRules()
    when (this[rulesetName]) {
        "classic reversi" -> ruleset = NxNReversiRules()
        "NxN reversi" -> ruleset = NxNReversiRules()
        "Anti reversi" -> ruleset = AntiReversiRules()
        "Othello" -> ruleset = OthelloRules()
    }
    return Game(ruleset, this[size], startPosition)
}

fun Game.toGamesInsertMap(): Map<Column<*>, Any?> = mapOf(
    Games.player1Name to this.getPlayer(0).getName(),
    Games.player2Name to this.getPlayer(1).getName(),
    Games.winnerName to if (this.getWinner()!!.first != 2) this.getPlayer(this.getWinner()!!.first).getName() else null,
    Games.size to this.getSize(),
    Games.moves to this.getMoveHistory(),
    Games.score to this.getWinner()!!.second.toString() + ":" + this.getWinner()!!.third.toString(),
    Games.rulesetName to this.getRulesetName()
)