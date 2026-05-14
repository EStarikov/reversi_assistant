class Game(private val ruleset: rules.NxNReversiRules, private val board: Board, private val gameID: Int) {
    private val players = ArrayList<Player>()
    private var currentPlayer = 0
    fun getCurrentPlayer(): Player {return players[currentPlayer]}
    fun makeMove(move: Position): Boolean {
        val flag = ruleset.tryAndApplyMove(board, move, players[currentPlayer])
        if (flag) switchPlayer()
        return flag
    }
    fun switchPlayer() {
        currentPlayer = 1 - currentPlayer
    }
    fun addPlayer(player: Player) {
        players.add(player)
    }
    fun getBoard(): Board {return board}
}