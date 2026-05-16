class PositionInHuman {
    fun makeHuman(position: Position): String {
        val humanRow = position.row + 1
        val humanCol = (position.col + 97).toChar()
        return "$humanCol$humanRow"
    }
}