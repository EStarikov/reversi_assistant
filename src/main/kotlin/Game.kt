class Game(private val ruleset: rules.NxNReversiRules, private val board: Board) {
    private val players = ArrayList<Player>()
    private var currentPlayer = 0
    private val moveHistory = ArrayList<String>()
    fun getCurrentPlayer(): Player {return players[currentPlayer]}
    fun makeMove(move: Position): Boolean {
        val flag = ruleset.tryAndApplyMove(board, move, players[currentPlayer])
        if (flag) {
            switchPlayer()
            moveHistory.add(PositionInHuman().makeHuman(move))
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
        return moveHistory.joinToString(" ")
    }
}