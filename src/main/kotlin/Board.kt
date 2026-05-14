class Board(private var size: Int) {
    private var board = Array(size) { Array(size) { Cell.EMPTY } }
    fun getSize() = board.size
    fun get(row: Int, col: Int) = board[row][col]
    fun set(row: Int, col: Int, cell: Cell) {board[row][col] = cell}
    fun isFull(): Boolean {
        return board.all { row -> row.all {cell -> cell != Cell.EMPTY} }
    }
}