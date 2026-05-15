import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class SimpleGameIntegrationTest {

    private lateinit var rulesOfThisGame: rules.NxNReversiRules
    private lateinit var playerOne: Player
    private lateinit var playerTwo: Player

 @Test
    fun `4x4 base rules`() {
        rulesOfThisGame = rules.NxNReversiRules()
        playerOne = Player(1, "Alice", 1200, 32, ColorOfPlayer.BLACK)
        playerTwo = Player(2, "Bob", 1200, 32, ColorOfPlayer.WHITE)
        val startPosition = listOf(Pair(Position(0, 0), 1), Pair(Position(1,1), 1), Pair(Position(0,1), 2), Pair(Position(1,0), 2))
        val board = rulesOfThisGame.createStartBoard(4, startPosition)
        val cleanGame = Game(rulesOfThisGame, board, 2)
        cleanGame.addPlayer(playerOne)
        cleanGame.addPlayer(playerTwo)


        val cleanBoard = cleanGame.getBoard()
        // Ход 1: Alice ставит на (1, 3)
        val move1 = Position(0, 2)
        assertTrue(rulesOfThisGame.isValidMove(cleanBoard, move1, playerOne))
        rulesOfThisGame.tryAndApplyMove(cleanBoard, move1, playerOne)

        // Проверяем, что фишка (1, 2) перевернулась
        assertEquals(Cell.PLAYER_ONE, cleanBoard.get(0, 1))
        assertEquals(Cell.PLAYER_ONE, cleanBoard.get(0, 2))

        // Ход 2: Bob ставит на (2, 4) - неверный ход
        val move2 = Position(1, 3)
        assertFalse(rulesOfThisGame.isValidMove(cleanBoard, move2, playerTwo))

        //Ход 2: Bob ставит на (2, 3)
        val move2r = Position(1, 2)
        assertTrue(rulesOfThisGame.isValidMove(cleanBoard, move2r, playerTwo))
        rulesOfThisGame.tryAndApplyMove(cleanBoard, move2r, playerTwo)


        // Ход 3: Alice ставит на (3, 3)
        val move3 = Position(2, 2)
        assertTrue(rulesOfThisGame.isValidMove(cleanBoard, move3, playerOne))
        rulesOfThisGame.tryAndApplyMove(cleanBoard, move3, playerOne)

        //Ход 4: Bob ставит на (2, 4)
        val move4 = Position(1, 3)
        assertTrue(rulesOfThisGame.isValidMove(cleanBoard, move4, playerTwo))
        rulesOfThisGame.tryAndApplyMove(cleanBoard, move4, playerTwo)

         // Ход 5: Alice ставит на (3, 1)
         val move5 = Position(2, 0)
         assertTrue(rulesOfThisGame.isValidMove(cleanBoard, move5, playerOne))
         rulesOfThisGame.tryAndApplyMove(cleanBoard, move5, playerOne)

         //Ход 6: Bob ставит на (4, 2)
         val move6 = Position(3, 1)
         assertTrue(rulesOfThisGame.isValidMove(cleanBoard, move6, playerTwo))
         rulesOfThisGame.tryAndApplyMove(cleanBoard, move6, playerTwo)

         // Ход 7: Alice ставит на (4, 4)
         val move7 = Position(3, 3)
         assertTrue(rulesOfThisGame.isValidMove(cleanBoard, move7, playerOne))
         rulesOfThisGame.tryAndApplyMove(cleanBoard, move7, playerOne)

         //Ход 6: Bob ставит на (4, 3)
         val move8 = Position(3, 2)
         assertTrue(rulesOfThisGame.isValidMove(cleanBoard, move8, playerTwo))
         rulesOfThisGame.tryAndApplyMove(cleanBoard, move8, playerTwo)

         // Ход 9: Alice ставит на (4, 1)
         val move9 = Position(3, 0)
         assertTrue(rulesOfThisGame.isValidMove(cleanBoard, move9, playerOne))
         rulesOfThisGame.tryAndApplyMove(cleanBoard, move9, playerOne)

        assertTrue(rulesOfThisGame.isGameOver(board, playerTwo))
        val winner = rulesOfThisGame.getWinner(cleanBoard)
        assertEquals(0, winner.first)
    }

    @Test
    fun `4x4 anti rules`() {
        rulesOfThisGame = rules.AntiReversiRules()
        playerOne = Player(1, "Alice", 1200, 32, ColorOfPlayer.BLACK)
        playerTwo = Player(2, "Bob", 1200, 32, ColorOfPlayer.WHITE)
        val startPosition = listOf(Pair(Position(0, 0), 1), Pair(Position(1,1), 1), Pair(Position(0,1), 2), Pair(Position(1,0), 2))
        val board = rulesOfThisGame.createStartBoard(4, startPosition)
        val cleanGame = Game(rulesOfThisGame, board, 2)
        cleanGame.addPlayer(playerOne)
        cleanGame.addPlayer(playerTwo)


        val cleanBoard = cleanGame.getBoard()
        // Ход 1: Alice ставит на (1, 3)
        val move1 = Position(0, 2)
        assertTrue(rulesOfThisGame.isValidMove(cleanBoard, move1, playerOne))
        rulesOfThisGame.tryAndApplyMove(cleanBoard, move1, playerOne)

        // Проверяем, что фишка (1, 2) перевернулась
        assertEquals(Cell.PLAYER_ONE, cleanBoard.get(0, 1))
        assertEquals(Cell.PLAYER_ONE, cleanBoard.get(0, 2))

        // Ход 2: Bob ставит на (2, 4) - неверный ход
        val move2 = Position(1, 3)
        assertFalse(rulesOfThisGame.isValidMove(cleanBoard, move2, playerTwo))

        //Ход 2: Bob ставит на (2, 3)
        val move2r = Position(1, 2)
        assertTrue(rulesOfThisGame.isValidMove(cleanBoard, move2r, playerTwo))
        rulesOfThisGame.tryAndApplyMove(cleanBoard, move2r, playerTwo)


        // Ход 3: Alice ставит на (3, 3)
        val move3 = Position(2, 2)
        assertTrue(rulesOfThisGame.isValidMove(cleanBoard, move3, playerOne))
        rulesOfThisGame.tryAndApplyMove(cleanBoard, move3, playerOne)

        //Ход 4: Bob ставит на (2, 4)
        val move4 = Position(1, 3)
        assertTrue(rulesOfThisGame.isValidMove(cleanBoard, move4, playerTwo))
        rulesOfThisGame.tryAndApplyMove(cleanBoard, move4, playerTwo)

        // Ход 5: Alice ставит на (3, 1)
        val move5 = Position(2, 0)
        assertTrue(rulesOfThisGame.isValidMove(cleanBoard, move5, playerOne))
        rulesOfThisGame.tryAndApplyMove(cleanBoard, move5, playerOne)

        //Ход 6: Bob ставит на (4, 2)
        val move6 = Position(3, 1)
        assertTrue(rulesOfThisGame.isValidMove(cleanBoard, move6, playerTwo))
        rulesOfThisGame.tryAndApplyMove(cleanBoard, move6, playerTwo)

        // Ход 7: Alice ставит на (4, 4)
        val move7 = Position(3, 3)
        assertTrue(rulesOfThisGame.isValidMove(cleanBoard, move7, playerOne))
        rulesOfThisGame.tryAndApplyMove(cleanBoard, move7, playerOne)

        //Ход 6: Bob ставит на (4, 3)
        val move8 = Position(3, 2)
        assertTrue(rulesOfThisGame.isValidMove(cleanBoard, move8, playerTwo))
        rulesOfThisGame.tryAndApplyMove(cleanBoard, move8, playerTwo)

        // Ход 9: Alice ставит на (4, 1)
        val move9 = Position(3, 0)
        assertTrue(rulesOfThisGame.isValidMove(cleanBoard, move9, playerOne))
        rulesOfThisGame.tryAndApplyMove(cleanBoard, move9, playerOne)

        assertTrue(rulesOfThisGame.isGameOver(board, playerTwo))
        val winner = rulesOfThisGame.getWinner(cleanBoard)
        assertEquals(1, winner.first)
    }

    @Test
    fun `othello rules`() {
        rulesOfThisGame = rules.OthelloRules()
        playerOne = Player(1, "Alice", 1200, 32, ColorOfPlayer.BLACK)
        playerTwo = Player(2, "Bob", 1200, 32, ColorOfPlayer.WHITE)
        val startPosition = listOf(Pair(Position(0, 0), 1))
        val board = rulesOfThisGame.createStartBoard(4, startPosition)
        val cleanGame = Game(rulesOfThisGame, board, 3)
        cleanGame.addPlayer(playerOne)
        cleanGame.addPlayer(playerTwo)


        val cleanBoard = cleanGame.getBoard()

        val moves = arrayOf(
            Position(4, 5), Position(5, 3), Position(2, 2), Position(2, 3), Position(3, 2),
            Position(3, 5), Position(5, 5), Position(2, 5), Position(5, 4), Position(6, 4),
            Position(6, 3), Position(5, 6), Position(7, 5), Position(6, 5), Position(4, 6),
            Position(3, 6), Position(2, 7), Position(2, 6), Position(4, 7), Position(7, 2),
            Position(1, 7), Position(4, 2), Position(4, 1), Position(3, 1), Position(3, 7),
            Position(7, 4), Position(5, 7), Position(2, 1), Position(1, 5), Position(5, 0),
            Position(2, 4), Position(1, 4), Position(0, 3), Position(5, 1), Position(1, 2),
            Position(1, 3), Position(0, 2), Position(6, 6), Position(5, 2), Position(6, 2),
            Position(4, 0), Position(1, 1), Position(3, 0), Position(1, 0), Position(2, 0),
            Position(0, 0), Position(6, 1), Position(6, 0), Position(7, 0), Position(7, 6),
            Position(7, 1), Position(7, 3), Position(0, 1), Position(0, 6), Position(7, 7),
            Position(0, 4), Position(0, 5), Position(6, 7), Position(0, 7), Position(1, 6)
        )

        for (move in moves) {
            assertTrue(rulesOfThisGame.isValidMove(cleanBoard, move, cleanGame.getCurrentPlayer()))
            cleanGame.makeMove(move)
        }

        assertTrue(cleanGame.isGameOver())
        val winner = cleanGame.getWinner()
        assertEquals(1, winner.first)
    }
}