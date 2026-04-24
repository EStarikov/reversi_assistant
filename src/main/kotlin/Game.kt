class Game(private val ruleset: BaseReversiRules, private val size: Int, private val gameID: Int) {
    private val board = Board(size)
    private val players = ArrayList<Player>()
    private var currentPlayer = 0
    fun makeMove(currentPlayer: Int, move: Position): Boolean {
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