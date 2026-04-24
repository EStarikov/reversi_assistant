import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class BaseReversiRulesIntegrationTest {

    private lateinit var rules: BaseReversiRules
    private lateinit var board: Board
    private lateinit var playerOne: Player
    private lateinit var playerTwo: Player
    private lateinit var game: Game

 @Test
    fun `should play full sequence of moves with flip validation`() {
        rules = BaseReversiRules()
        playerOne = Player(1, "Alice", 1200, 32, ColorOfPlayer.BLACK)
        playerTwo = Player(2, "Bob", 1200, 32, ColorOfPlayer.WHITE)
        val cleanGame = Game(rules, 4, 2)
        cleanGame.addPlayer(playerOne)
        cleanGame.addPlayer(playerTwo)

        // Начальная расстановка
        val cleanBoard = cleanGame.getBoard()
        cleanBoard.set(0, 0, Cell.PLAYER_ONE)
        cleanBoard.set(0, 1, Cell.PLAYER_TWO)
        cleanBoard.set(1, 0, Cell.PLAYER_TWO)
        cleanBoard.set(1, 1, Cell.PLAYER_ONE)

        // Проверяем начальное состояние
        assertEquals(Cell.PLAYER_ONE, cleanBoard.get(0, 0))
        assertEquals(Cell.PLAYER_TWO, cleanBoard.get(0, 1))

        // Ход 1: Alice (PLAYER_ONE) ставит на (1, 3)
        val move1 = Position(0, 2)
        assertTrue(rules.isValidMove(cleanBoard, move1, playerOne))
        rules.tryAndApplyMove(cleanBoard, move1, playerOne)

        // Проверяем, что фишка (1, 2) перевернулась
        assertEquals(Cell.PLAYER_ONE, cleanBoard.get(0, 1))
        assertEquals(Cell.PLAYER_ONE, cleanBoard.get(0, 2))

        // Ход 2: Bob (PLAYER_TWO) ставит на (2, 4)
        val move2 = Position(1, 3)
        assertFalse(rules.isValidMove(cleanBoard, move2, playerTwo))


        // Ход 3: Alice ставит на (3, 1)
        val move3 = Position(2, 0)
        assertTrue(rules.isValidMove(cleanBoard, move3, playerOne))
        rules.tryAndApplyMove(cleanBoard, move3, playerOne)


    }
}