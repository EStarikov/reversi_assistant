fun main() {
    println("Игра Реверси")
    println("Введите первого игрока")
    val (id1, name1, elo1, chips1) = readln().split(' ')
    val playerOne = Player(id1.toInt(), name1, elo1.toInt(), chips1.toInt(), ColorOfPlayer.BLACK)
    println("Введите первого игрока")
    val (id2, name2, elo2, chips2) = readln().split(' ')
    val playerTwo = Player(id2.toInt(), name2, elo2.toInt(), chips2.toInt(), ColorOfPlayer.WHITE)
    println("Введите стартовую позицию первого игрока")
    val (row1, col1) = readln().split(' ').map(String::toInt)
    val (row2, col2) = readln().split(' ').map(String::toInt)
    println("Введите стартовую позицию второго игрока")
    val (row3, col3) = readln().split(' ').map(String::toInt)
    val (row4, col4) = readln().split(' ').map(String::toInt)
    val startPosition = listOf(Pair(Position(row1, col1), 1), Pair(Position(row2, col2), 1), Pair(Position(row3, col3), 2), Pair(Position(row4, col4), 2))
    val rules = BaseReversiRules()
    val board = rules.createStartBoard(8, startPosition)
    val game = Game(rules, board, 1)
    game.addPlayer(playerOne)
    game.addPlayer(playerTwo)
    while (!rules.isGameOver(board, game.getCurrentPlayer())) {
        val currentPlayer = game.getCurrentPlayer()
        println("Сделайте ход")
        val (row, col) = readln().split(' ').map(String::toInt)
        val pos = Position(row, col)
        if (!rules.isValidMove(board, pos, currentPlayer)) {
            println("Сделайте валидный ход")
            continue
        }
        rules.tryAndApplyMove(board, pos, currentPlayer)
        game.switchPlayer()
    }
    val num = rules.getWinner(board)
    println("Выйграл игрок №, $num")
}
