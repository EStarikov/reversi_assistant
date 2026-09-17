package gui

import java.awt.*
import javax.swing.*
import Game
import board.Cell
import board.Position
import player.Player
import rules.NxNReversiRules
import rules.OthelloRules

class GuiReversi(private val player1: Player, private val player2: Player, private val rules: NxNReversiRules, private val boardSize: Int) : JPanel() {
    private val buttons = Array(boardSize) { arrayOfNulls<JButton>(boardSize) }
    private var setupMoves = mutableListOf<Pair<Position, Int>>()
    private var currentPlayer = 1
    private lateinit var game: Game
    private var gameStarted = false

    init {
        layout = GridLayout(boardSize, boardSize)

        val isOthello = rules is OthelloRules

        if (isOthello) {
            setupMoves.addAll(listOf(
                Pair(Position(boardSize/2 - 1, boardSize/2 - 1), 2),
                Pair(Position(boardSize/2 - 1, boardSize/2), 1),
                Pair(Position(boardSize/2, boardSize/2 - 1), 1),
                Pair(Position(boardSize/2, boardSize/2), 2)
            ))
            game = Game(rules, boardSize, setupMoves.toTypedArray())
            game.addPlayer(player1)
            game.addPlayer(player2)
            gameStarted = true
        }

        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                val button = JButton()
                button.background = getCellColor(i, j)
                button.isOpaque = true
                val row = i
                val col = j

                button.addActionListener {
                    if (!gameStarted && setupMoves.size < 4) {
                        setupMoves.add(Pair(Position(row, col), currentPlayer))
                        button.background = if (currentPlayer == 1) Color.BLACK else Color.WHITE
                        currentPlayer = if (currentPlayer == 1) 2 else 1

                        if (setupMoves.size == 4) {
                            game = Game(rules, boardSize, setupMoves.toTypedArray())
                            game.addPlayer(player1)
                            game.addPlayer(player2)
                            gameStarted = true
                            updateBoard()
                        }
                    } else if (gameStarted) {
                        val move = Position(row, col)
                        if (game.makeMove(move)) {
                            updateBoard()
                            checkGameOver()
                        } else {
                            JOptionPane.showMessageDialog(this, "Invalid move!")
                        }
                    }
                }

                buttons[i][j] = button
                add(button)
            }
        }
    }

    private fun getCellColor(row: Int, col: Int): Color {
        if (::game.isInitialized && gameStarted) {
            val cell = game.getBoard().get(row, col)
            return when (cell) {
                Cell.PLAYER_ONE -> Color.BLACK
                Cell.PLAYER_TWO -> Color.WHITE
                else -> Color.GREEN
            }
        }
        return Color.GREEN
    }

    private fun updateBoard() {
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                val color = when (game.getBoard().get(i, j)) {
                    Cell.PLAYER_ONE -> Color.BLACK
                    Cell.PLAYER_TWO -> Color.WHITE
                    else -> Color.GREEN
                }
                buttons[i][j]?.background = color
                buttons[i][j]?.repaint()
            }
        }
    }

    private fun checkGameOver() {
        if (game.isGameOver()) {
            val winner = game.getWinner()
            val message = when {
                winner == null -> "Draw!"
                winner.first == 1 -> "${player1.getName()} wins!"
                else -> "${player2.getName()} wins!"
            }
            JOptionPane.showMessageDialog(this, message)
        }
    }
}