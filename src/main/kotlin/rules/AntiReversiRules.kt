package rules

import Board

class AntiReversiRules: NxNReversiRules() {
    override fun getWinner(board: Board): Int {
        var winner = NxNReversiRules().getWinner(board)
        if (winner < 2) winner = 1 - winner
        return winner
    }
}