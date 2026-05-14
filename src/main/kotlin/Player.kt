class Player(private val id: Int, private val name: String, private var elo: Int, private var chipsCount: Int, private val color: ColorOfPlayer) {
    fun getColor(): Cell {
        if (color == ColorOfPlayer.BLACK) return Cell.PLAYER_ONE
        return Cell.PLAYER_TWO
    }
    fun getOppositeColor(): Cell {
        if (color == ColorOfPlayer.BLACK) return Cell.PLAYER_TWO
        return Cell.PLAYER_ONE
    }
}