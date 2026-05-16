import Games.player1Name
import rules.AntiReversiRules
import rules.NxNReversiRules
import rules.OthelloRules

fun main() {
    DataBaseFactory.init()
    val repository = GameRepository()
    var player1:Player? = null
    var player2:Player? = null
    println("Enter 1 to select player or Enter 2 to register new player:")
    println("Enter name:")
    val player1Name = readln()
    val input = readln().toInt()
    if (input == 1) {
        player1 = repository.createPlayer(player1Name)
    }
    else { player1 = repository.getPlayerByName(player1Name) }
    println("Enter 1 to select player or Enter 2 to register new player:")
    println("Enter name:")
    val player2Name = readln()
    val input2 = readln().toInt()
    if (input2 == 1) {
        player2 = repository.createPlayer(player2Name)
    }
    else { player2 = repository.getPlayerByName(player2Name) }
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
                1 -> {game = Game(NxNReversiRules(), NxNReversiRules().createStartBoard(positions = startPosition))}
                in 2..3 -> {
                    println("Enter the board's size (less than 26)")
                    val size = readln().toInt()
                    if (size !in 0..26) throw IllegalArgumentException("Board's size must be between 0 and 26")
                    when (chosenRuleset) {
                        2 -> {game = Game(NxNReversiRules(), NxNReversiRules().createStartBoard(size, startPosition))}
                        3 -> {game = Game(AntiReversiRules(), AntiReversiRules().createStartBoard(size, startPosition))}
                    }
                }
            }
        }
        4 -> {game = Game(OthelloRules(), OthelloRules().createStartBoard())}
    }
    game!!.addPlayer(player1!!)
    game.addPlayer(player2!!)
    println("Enter \"Stop\" to exit")
    while (!game!!.isGameOver()) {
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
    repository.saveGame(game)
}
