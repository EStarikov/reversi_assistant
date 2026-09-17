import board.Position
import board.PositionInProgram
import database.DatabaseFactory
import database.Games
import database.GenericRepository
import database.Players
import database.toGameEntity
import database.toGamesInsertMap
import database.toPlayerEntity
import database.toPlayersInsertMap
import player.Player
import rules.AntiReversiRules
import rules.NxNReversiRules
import rules.OthelloRules
import java.lang.Math.pow

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
    var player1: Player?
    var player2: Player?
    var id1: Int?
    var id2: Int? = null
    println("Enter 1 to select player or Enter 2 to register new player:")
    println("Enter name if you want to create new player or enter id of existing player:")
    val input = readln().toInt()
    val player1Name = readln()
    if (input == 2) {
        player1 = Player(player1Name)
        id1 = playersRepo.saveItem(player1)
    }
    else { id1 = player1Name.toInt()
        player1 = playersRepo.getById(id1)
    }
    println("Enter 1 to select player or Enter 2 to register new player:")
    println("Enter name if you want to create new player or enter id of existing player:")
    val input2 = readln().toInt()
    val player2Name = readln()
    if (input2 == 2) {
        player2 = Player(player2Name)
        id2 =  playersRepo.saveItem(player2)
    }
    else { id2 = player2Name.toInt()
        player2 = playersRepo.getById(id2)
    }
    println("Select version of rules:")
    println("1 - Classic reversi")
    println("2 - NxN reversi")
    println("3 - Anti reversi")
    println("4 - Othello")
    var game: Game? = null
    println("Enter -1 to exit.")
    var chosenRuleset = readln().toInt()
    while (true) {
        if (chosenRuleset == -1) return
        if (chosenRuleset in 1..4) break
        println("Enter permissible value:")
        chosenRuleset = readln().toInt()
    }
    when (chosenRuleset) {
        in 1..3 -> {
            val startPosition = Array(4) { index ->
                Pair(Position(index, index), index)
            }
            println("Enter the starting position (positions are entered in turn)")
            val moves = readln().split(" ")
            for ((index, value) in moves.withIndex()) {
                startPosition[index] = Pair(PositionInProgram().makeProgram(value), (index + 1) % 2)
            }
            when (chosenRuleset) {
                1 -> {game = Game(NxNReversiRules(), positions = startPosition)}
                in 2..3 -> {
                    println("Enter the board's size (less than 26)")
                    val size = readln().toInt()
                    if (size !in 0..26) throw IllegalArgumentException("Board's size must be between 0 and 26")
                    when (chosenRuleset) {
                        2 -> {game = Game(NxNReversiRules(), size, startPosition)}
                        3 -> {game = Game(AntiReversiRules(), size, startPosition)}
                    }
                }
            }
        }
        4 -> {game = Game(OthelloRules())}
    }
    game!!.addPlayer(player1!!)
    game.addPlayer(player2!!)
    println("Enter \"Stop\" to exit")
    while (!game.isGameOver()) {
        val currentPlayer = game.getCurrentPlayer()
        val name = currentPlayer.getName()
        println("Enter $name's move:")
        val move = readln()
        if (move == "Stop") return
        if (game.makeMove(PositionInProgram().makeProgram(move))) {
            continue
        }
        println("Make valid move")
    }
    val winnerInfo = game.getWinner()
    println(winnerInfo)
    var newElo1 = 10.0
    var newElo2 = 20.0
    if (winnerInfo!!.first < 2) {
        newElo1 = player1.getElo() + 16 * ((1 - winnerInfo.first) - (1/(1 + pow(10.toDouble(), (player1.getElo() - player2.getElo()).toDouble()/400))))
        newElo2 = player2.getElo() + 16 * ((winnerInfo.first) - (1/(1 + pow(10.toDouble(), (player1.getElo() - player2.getElo()).toDouble()/400))))
    } else {
        newElo1 = player1.getElo() + 16 * ((1 - 0.5 - (1/(1 + pow(10.toDouble(), (player1.getElo() - player2.getElo()).toDouble()/400)))))
        newElo2 = player2.getElo() + 16 * ((1 - 0.5 - (1/(1 + pow(10.toDouble(), (player1.getElo() - player2.getElo()).toDouble()/400)))))
    }
    playersRepo.updateItem(id1, newElo1)
    playersRepo.updateItem(id2, newElo2)
    gamesRepo.saveItem(game)
}
