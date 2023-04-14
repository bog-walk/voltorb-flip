package dev.bogwalk.model

class GameGrid(
    private val generator: LevelGenerator = GameLevelGenerator()
) {
    private val board: List<MutableList<GameTile>> = List(5) { row ->
        MutableList(5) { col ->
            GameTile(row to col, 1)
        }
    }
    val tiles: List<GameTile>
        get() = board.flatten()
    var numOfFlippedTiles = 0

    fun getSummary(isEmpty: Boolean = false): List<Pair<Int, Int>> {
        if (isEmpty) return List(10) { 0 to 0 }

        val summary = mutableListOf<Pair<Int, Int>>()

        for (row in board) {
            summary += row.sumOf { it.value } to row.count { it.value == 0 }
        }
        for (col in 0..4) {
            var values = 0
            var zeroes = 0
            for (row in 0..4) {
                when (val v = board[row][col].value) {
                    0 -> zeroes++
                    else -> values += v
                }
            }
            summary += values to zeroes
        }

        return summary
    }

    /**
     * Resets current board to either be empty or filled with new level data.
     *
     * @return Maximum amount of coins for randomly selected level, or 0 if empty board generated.
     */
    fun reset(level: Int, toEmpty: Boolean = false): Int {
        numOfFlippedTiles = 0

        val data = if (toEmpty) emptyList() else generator.getLevelData(level)
        val assignedData = if (toEmpty) emptyList() else generator.assignLevelData(data)
        for (row in 0..4) {
            for (col in 0..4) {
                board[row][col] = GameTile(
                    row to col,
                    assignedData.getOrNull(row * 5 + col) ?: 1
                )
            }
        }

        return data.getOrNull(3) ?: 0
    }

    /**
     * Changes state of selected tile in board to flipped.
     *
     * @return Value of flipped over tile.
     */
    fun select(position: Pair<Int, Int>): Int {
        numOfFlippedTiles++

        val (row, col) = position
        board[row][col] = board[row][col].copy(isFlipped = true)

        return board[row][col].value
    }

    /**
     * Edits stored memo boolean array of selected tile by toggling the indexed value.
     */
    fun edit(position: Pair<Int, Int>, index: Int) {
        val (row, col) = position
        val current = board[row][col].memoData[index]
        board[row][col] = board[row][col].copy(
            memoData = board[row][col].memoData.apply { set(index, !current) }
        )
    }

    /**
     * Changes state of all board files to flipped.
     *
     * [numOfFlippedTiles] is not altered as this only happens on game round loss/win.
     */
    fun reveal() {
        for (row in 0..4) {
            for (col in 0..4) {
                board[row][col] =  board[row][col].copy(isFlipped = true)
            }
        }
    }
}