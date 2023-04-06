package dev.bogwalk.ui.util

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.bogwalk.model.GameLevelGenerator
import dev.bogwalk.model.LevelGenerator

class VoltorbFlipAppState(
    private val generator: LevelGenerator = GameLevelGenerator()
) {
    var currentLevel by mutableStateOf(1)
    var screenState by mutableStateOf(Screen.PRE_GAME)
    var totalCoins by mutableStateOf(0)
    var currentCoins by mutableStateOf(0)

    var grid by mutableStateOf(generator.generateEmptyLevel())
    var infoGrid by mutableStateOf(generator.getEmptySummary())
    var currentPosition by mutableStateOf(-1 to -1)
    var isMemoOpen by mutableStateOf(false)

    private var maxLevelCoins = 0
    private var numOfFlippedTiles = 0
    private var winningStreak = 0

    fun selectATile(position: Pair<Int, Int>) {
        if (!isMemoOpen) {
            grid[position.first][position.second].isFlipped = true
            when (val v = grid[position.first][position.second].value) {
                0 -> {
                    changeLevel(isAWin = false)
                    resetGame()
                    return
                }
                1 -> if (currentCoins == 0) currentCoins = 1
                2, 3 -> currentCoins = if (currentCoins == 0) v else currentCoins * v
            }
            if (currentCoins == maxLevelCoins) {
                changeLevel(isAWin = true)
                resetGame()
                return
            }
            numOfFlippedTiles++
        }
        currentPosition = position
    }

    fun editCurrentTile(memoToEdit: Int) {
        val (row, col) = currentPosition
        val current = grid[row][col].memoData[memoToEdit]
        grid[row][col].memoData[memoToEdit] = !current
    }

    fun endGame() {
        currentPosition = -1 to -1
        screenState = Screen.QUITTING
        // open dialog? or speech box confirming quit
        totalCoins += currentCoins
        currentCoins = 0
    }

    fun resetGame() {
        screenState = Screen.IN_GAME
        currentPosition = 0 to 0
        isMemoOpen = false

        val data = generator.getLevelData(currentLevel)
        maxLevelCoins = data[3]
        grid = generator.generateLevelTiles(data)
        infoGrid = generator.getTilesSummary(grid)

        numOfFlippedTiles = 0
    }

    private fun changeLevel(isAWin: Boolean) {
        if (isAWin) {
            winningStreak++
            currentLevel = if (winningStreak >= 5) 7 else (currentLevel + 1).coerceAtMost(7)
        } else {
            winningStreak = 0
            currentLevel = if (numOfFlippedTiles < currentLevel + 1) {
                numOfFlippedTiles.coerceAtLeast(0)
            } else currentLevel--
        }
        totalCoins += currentCoins
        currentCoins = 0
    }
}

enum class Screen {
    ABOUT_GAME, ABOUT_HINT, ABOUT_MEMO, PRE_INFO, PRE_GAME, IN_GAME, QUITTING
}