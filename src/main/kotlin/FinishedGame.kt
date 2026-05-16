import java.time.LocalDateTime

class FinishedGame(private val id: Int = 0,
                   private val player1Name: String,
                   private val player2Name: String,
                   private val winnerName: String?,
                   private val score: String,
                   private val moves: String,
                   private val playedAt: LocalDateTime = LocalDateTime.now()) {
}