class PositionInProgram {
    fun makeProgram(position: String): Position {
        return Position(position[1].code - 1, position[0].code - 97)
    }
}