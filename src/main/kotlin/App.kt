import database.DatabaseFactory
import database.Games
import database.GenericRepository
import database.Players
import database.toGameEntity
import database.toGamesInsertMap
import database.toPlayerEntity
import database.toPlayersInsertMap
import gui.GameFrame
import javax.swing.SwingUtilities


fun main() {
    DatabaseFactory.init()
    val playersRepo = GenericRepository(
        table = Players,
        toEntity = { row -> row.toPlayerEntity() },
        toInsertMap = { player -> player.toPlayersInsertMap() }
    )
    val gamesRepo = GenericRepository(
        table = Games,
        toEntity = { row -> row.toGameEntity() },
        toInsertMap = { player -> player.toGamesInsertMap() }
    )

    SwingUtilities.invokeLater { GameFrame(playersRepo, gamesRepo).isVisible = true }
}