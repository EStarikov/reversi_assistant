class Position(var row: Int, var col: Int) {
    fun isOnBoard(board: Board): Boolean {
        return (row in 0 until board.getSize() && col in 0 until board.getSize())
    }
}