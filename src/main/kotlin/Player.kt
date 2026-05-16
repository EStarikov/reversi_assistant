import java.time.LocalDateTime

class Player(private val name: String, private val elo: Int = 1000, registeredAt: LocalDateTime = LocalDateTime.now()) {
    private var color: ColorOfPlayer? = null
    fun setColor(color: ColorOfPlayer) {this.color = color }
    fun getColor(): Cell {
        if (color == ColorOfPlayer.BLACK) return Cell.PLAYER_ONE
        return Cell.PLAYER_TWO
    }
    fun getOppositeColor(): Cell {
        if (color == ColorOfPlayer.BLACK) return Cell.PLAYER_TWO
        return Cell.PLAYER_ONE
    }
    fun getName(): String {return name}
    fun getElo(): Int {return elo}
}