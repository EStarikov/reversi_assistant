package gui

import java.awt.GridLayout
import javax.swing.BorderFactory
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField

class PlayerNameInput(private val onNamesEntered: (String, String) -> Unit) : JPanel() {
    init {
        layout = GridLayout(3, 2, 10, 10)
        border = BorderFactory.createEmptyBorder(50, 50, 50, 50)

        add(JLabel("Player 1 (Black):"))
        val name1 = JTextField()
        add(name1)

        add(JLabel("Player 2 (White):"))
        val name2 = JTextField()
        add(name2)

        val startButton = JButton("Start Game")
        startButton.addActionListener {
            val p1 = name1.text.ifEmpty { "Player 1" }
            val p2 = name2.text.ifEmpty { "Player 2" }
            onNamesEntered(p1, p2)
        }
        add(startButton)
    }
}