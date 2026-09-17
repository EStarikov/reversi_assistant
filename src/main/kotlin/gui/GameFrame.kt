package gui

import Game
import database.GenericRepository
import player.Player
import rules.AntiReversiRules
import rules.NxNReversiRules
import rules.OthelloRules
import java.awt.*
import javax.swing.*

class GameFrame(private val playersRepo: GenericRepository<Player>, private val gamesRepo: GenericRepository<Game>) : JFrame() {
    private val cards = CardLayout()
    private val mainPanel = JPanel(cards)

    private var player1: Player? = null
    private var player2: Player? = null
    private var selectedRules: NxNReversiRules? = null
    private var boardSize = 8

    init {
        title = "Reversi"
        setSize(900, 900)
        defaultCloseOperation = EXIT_ON_CLOSE

        val startMenu = JPanel().apply {
            add(JButton("Start game").apply {
                addActionListener { cards.show(mainPanel, "playerInput") }
            })
        }

        val playerInput = PlayerNameInput { name1, name2 ->
            player1 = Player(name1)
            player2 = Player(name2)
            cards.show(mainPanel, "rulesSelection")
        }

        val rulesSelection = JPanel().apply {
            add(JButton("Classic reversi").apply {
                addActionListener {
                    selectedRules = NxNReversiRules()
                    boardSize = 8
                    startGame()
                }
            })
            add(JButton("Othello").apply {
                addActionListener {
                    selectedRules = OthelloRules()
                    boardSize = 8
                    startGame()
                }
            })
            add(JButton("NxN reversi").apply {
                addActionListener {
                    selectedRules = NxNReversiRules()
                    cards.show(mainPanel, "boardSizeInput")
                }
            })
            add(JButton("Anti reversi").apply {
                addActionListener {
                    selectedRules = AntiReversiRules()
                    cards.show(mainPanel, "boardSizeInput")
                }
            })
        }

        val boardSizeInput = JPanel().apply {
            layout = GridLayout(2, 2, 10, 10)
            border = BorderFactory.createEmptyBorder(50, 50, 50, 50)

            add(JLabel("Board size:"))
            val sizeField = JTextField("8")
            add(sizeField)

            add(JButton("Start").apply {
                addActionListener {
                    boardSize = sizeField.text.toIntOrNull()?.takeIf { it in 4..20 } ?: 8
                    startGame()
                }
            })
        }

        mainPanel.add(startMenu, "startMenu")
        mainPanel.add(playerInput, "playerInput")
        mainPanel.add(rulesSelection, "rulesSelection")
        mainPanel.add(boardSizeInput, "boardSizeInput")
        add(mainPanel)
    }

    private fun startGame() {
        val gamePanel = GuiReversi(player1!!, player2!!, selectedRules!!, boardSize)
        mainPanel.add(gamePanel, "gamePanel")
        cards.show(mainPanel, "gamePanel")
    }
}