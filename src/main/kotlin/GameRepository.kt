import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.less
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class GameRepository {
    fun createPlayer(name: String, initialElo: Int = 1000): Player {
        val existing = transaction {
            Players.selectAll().where { Players.name eq name }.singleOrNull()
        }
        if (existing != null) {
            throw IllegalArgumentException("Игрок с именем '$name' уже существует")
        }

        transaction {
            Players.insert {
                it[Players.name] = name
                it[Players.elo] = initialElo
            }
        }
        return Player(name, initialElo)
    }

    fun getAllPlayers(): List<Player> = transaction {
        Players.selectAll()
            .orderBy(Players.elo to SortOrder.DESC)
            .map { row ->
                Player(
                    name = row[Players.name],
                    elo = row[Players.elo],
                    registeredAt = row[Players.registeredAt],
                )
            }
    }

    fun getPlayerByName(name: String): Player? = transaction {
        Players.selectAll().where { Players.name eq name }
            .map { row ->
                Player(
                    name = row[Players.name],
                    elo = row[Players.elo],
                    registeredAt = row[Players.registeredAt]
                )
            }
            .singleOrNull()
    }

    fun updatePlayerElo(name: String, newElo: Int): Boolean {
        return transaction {
            Players.update({ Players.name eq name }) {
                it[Players.elo] = newElo
            } > 0
        }
    }

    fun deletePlayer(name: String): Boolean {
        return transaction {
            Players.deleteWhere { (Players.name eq name) and (Players.elo less 0) } > 0
        }
    }

    fun getPlayersCount(): Long = transaction {
        Players.selectAll().count()
    }

    fun saveGame(game: Game): Int {
        return transaction {
            Games.insertAndGetId {
                it[Games.player1Name] = game.getPlayer(0).getName()
                it[Games.player2Name] = game.getPlayer(1).getName()
                val winner = game.getWinner()
                if (winner != null) {it[Games.winnerName] = game.getPlayer(winner.first).getName()}
                else it[Games.winnerName] = null
                val score = "${winner?.second}:${winner?.second}"
                it[Games.score] = score
                it[Games.moves] = game.getMoveHistory()
            }.value
        }
    }

    fun getAllGames(): List<FinishedGame> = transaction {
        Games.selectAll()
            .orderBy(Games.playedAt to SortOrder.DESC)
            .map { row ->
                FinishedGame(
                    id = row[Games.id].value,
                    player1Name = row[Games.player1Name],
                    player2Name = row[Games.player2Name] ?: "",
                    winnerName = row[Games.winnerName],
                    score = row[Games.score],
                    moves = row[Games.moves],
                    playedAt = row[Games.playedAt]
                )
            }
    }

    fun clearAllData() {
        DataBaseFactory.clearAllData()
    }
}