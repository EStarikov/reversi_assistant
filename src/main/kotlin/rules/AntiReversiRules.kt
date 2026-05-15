package rules

import Board

class AntiReversiRules: NxNReversiRules() {
    override fun getWinner(board: Board): Triple<Int, Int, Int> {
        val winner = NxNReversiRules().getWinner(board)
        if (winner.first < 2) return Triple(1 - winner.first, winner.second, winner.third)
        return winner
    }
}