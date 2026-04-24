open class BaseReversiRules {
    open fun createStartBoard(size: Int, positions: List<Pair<Position, Int>>): Board {
        val board = Board(size)
        for (x in positions) {
            val pos = x.first
            val player = x.second
            if (!pos.isOnBoard(board)) throw NoSuchElementException()
            if (player == 1) board.set(pos.row, pos.col, Cell.PLAYER_ONE)
            else board.set(pos.row, pos.col, Cell.PLAYER_TWO)
        }
        return board
    }
    fun validAndMoveCells(board: Board, move: Position, player: Player, onValidCell: (row: Int, col: Int) -> Boolean): Boolean {
        var found = false
        if (move.isOnBoard(board) && board.get(move.row, move.col) == Cell.EMPTY) {
            for (i in move.row - 1 until move.row + 2) {
                for (j in move.col - 1 until move.col + 2) {
                    if (Position(i, j).isOnBoard(board) && board.get(i, j) == player.getOppositeColor()) {
                        val diffRow = i - move.row
                        val diffCol = j - move.col
                        val poses = mutableListOf<Pair<Int, Int>>()
                        poses.add(Pair(i, j))
                        var currentRow = i + diffRow
                        var currentCol = j + diffCol
                        while (Position(currentRow, currentCol).isOnBoard(board)) {
                            when (board.get(currentRow, currentCol)) {
                                player.getOppositeColor() -> {
                                    poses.add(Pair(currentRow, currentCol))
                                    currentRow += diffRow
                                    currentCol += diffCol
                                }
                                player.getColor() -> {
                                    found = true
                                    for ((x, y) in poses) {
                                        if (!onValidCell(x, y)) return found
                                    }
                                    onValidCell(move.row, move.col)
                                    break
                                }
                                else -> break
                            }
                        }
                    }
                }
            }
        }
        return found
    }
    fun isValidMove(board: Board, move: Position, player: Player): Boolean {
        return validAndMoveCells(board, move, player) { _, _ ->
            false
        }
    }
    fun tryAndApplyMove(board: Board, move: Position, player: Player): Boolean {
        val flag = validAndMoveCells(board, move, player) { i, j ->
            board.set(i, j, player.getColor())
            true
        }
        return flag
    }
    fun getValidMoves(board: Board, player: Player): List<Position> {
        val moves = mutableListOf<Position>()
        for (i in 0 until board.getSize()) {
            for (j in 0 until board.getSize()) {
                if (isValidMove(board, Position(i, j), player)) {
                    moves.add(Position(i, j))
                }
            }
        }
        return moves
    }
    open fun isGameOver(board: Board, player: Player): Boolean {
        val moves = getValidMoves(board, player)
        return moves.isEmpty()
    }
    open fun getWinner(board: Board): Int {
        var pl1 = 0
        var pl2 = 0
        for (i in 0 until board.getSize()) {
            for (j in 0 until board.getSize()) {
                if (board.get(i, j) == Cell.PLAYER_ONE) pl1 += 1
                else pl2 += 1
            }
        }
        return when {
            pl1 > pl2 -> 0
            pl2 > pl1 -> 1
            else -> 2
        }
    }
}
