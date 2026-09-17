package board

class PositionInProgram {
    fun makeProgram(position: String): Position {
        return Position(position[1].digitToInt() - 1, position[0].code - 97)
    }
}