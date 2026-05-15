class PositionInHuman(position: Position) {
    val row = position.row
    val col = position.col
    fun makeHuman(): String {
        val humanRow = row + 1
        val humanCol = (col + 97).toChar()
        return "$humanCol$humanRow"
    }
}