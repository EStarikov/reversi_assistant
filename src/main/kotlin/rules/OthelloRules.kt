package rules

import Board
import Cell
import Player
import Position

class OthelloRules: NxNReversiRules() {
    override fun createStartBoard(size: Int, positions: Array<Pair<Position, Int>>): Board {
        val board = Board(8)
        board.set(3, 3, Cell.PLAYER_TWO)
        board.set(4, 4, Cell.PLAYER_TWO)
        board.set(3, 4, Cell.PLAYER_ONE)
        board.set(4, 3, Cell.PLAYER_ONE)
        return board
    }
    override fun isGameOver(board: Board, player: Player): Boolean {
        return board.isFull()
    }
}