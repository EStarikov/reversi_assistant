import board.Board
import board.Position
import board.PositionInHuman
import player.Player
import rules.AntiReversiRules
import rules.NxNReversiRules
import rules.OthelloRules

class Game(private val ruleset: NxNReversiRules, private val size: Int = 8, positions: Array<Pair<Position, Int>> = arrayOf(Pair(Position(0, 0), 1))) {
    private val board = ruleset.createStartBoard(size, positions)
    private val players = ArrayList<Player>()
    private var currentPlayer = 0
    private val moveHistory = positions.map  { it. first } .toMutableList()
    fun getCurrentPlayer(): Player {return players[currentPlayer]}
    fun makeMove(move: Position): Boolean {
        val flag = ruleset.tryAndApplyMove(board, move, players[currentPlayer])
        if (flag) {
            switchPlayer()
            moveHistory.add(move)
        }
        return flag
    }
    fun switchPlayer() {
        currentPlayer = 1 - currentPlayer
    }
    fun addPlayer(player: Player) {
        players.add(player)
    }
    fun getBoard(): Board {return board}
    fun isGameOver(): Boolean {
        return ruleset.isGameOver(board, getCurrentPlayer())
    }
    fun getWinner(): Triple<Int, Int, Int>? {
        if (!isGameOver()) return null
        return ruleset.getWinner(board)
    }
    fun getPlayer(index: Int): Player {return players[index]}
    fun getMoveHistory(): String {
        return moveHistory.joinToString(" ") { PositionInHuman().makeHuman(it) }
    }
    fun getSize(): Int { return size }
    fun getRulesetName(): String {
        var str = "a"
        when (ruleset) {
            NxNReversiRules() -> str = if (size == 8) "classic reversi" else "NxN reversi"
            AntiReversiRules() -> str = "Anti reversi"
            OthelloRules() -> str = "Othello"
        }
        return str
    }
}