package dev.bogwalk.ui.util

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.bogwalk.model.GameGrid
import dev.bogwalk.ui.Screen

class VoltorbFlipAppState(
    private val grid: GameGrid = GameGrid()
) {
    var currentLevel by mutableStateOf(1)
    var screenState by mutableStateOf(Screen.PRE_GAME) // should this be hoisted?
    var totalCoins by mutableStateOf(0)
    var currentCoins by mutableStateOf(0)
    var newCoin: Int? by mutableStateOf(1)

    var gameTiles by mutableStateOf(grid.tiles)
    var infoSummary by mutableStateOf(grid.getSummary(isEmpty = true))
    var currentPosition by mutableStateOf(-2 to -2)
    var isMemoOpen by mutableStateOf(false)

    private var maxLevelCoins = 0
    private var winningStreak = 0
    private var shouldAdvance = false
    private var isFirstDouble = true
    private var isFirstTriple = true

    fun selectATile(position: Pair<Int, Int>) {
        if (!isMemoOpen) {
            when (val v = grid.select(position)) {
                0 -> {
                    changeLevel(isAWin = false)
                    return
                }
                1 -> if (currentCoins == 0) currentCoins = 1
                2 -> {
                    currentCoins = if (currentCoins == 0) v else currentCoins * v
                    if (isFirstDouble) {
                        newCoin = 2
                        screenState = Screen.REVEAL
                        isFirstDouble = false
                    }
                }
                3 -> {
                    currentCoins = if (currentCoins == 0) v else currentCoins * v
                    if (isFirstTriple) {
                        newCoin = 3
                        screenState = Screen.REVEAL
                        isFirstTriple = false
                    }
                }
            }
            if (currentCoins == maxLevelCoins) {
                screenState = Screen.GAME_WON
                winningStreak++
                shouldAdvance = true
                newCoin = null  // game won takes precedence
            }
        }
        currentPosition = position
        gameTiles = grid.tiles
    }

    fun editCurrentTile(memoToEdit: Int) {
        grid.edit(currentPosition, memoToEdit)
        gameTiles = grid.tiles
    }

    fun endGame() {  // should this be placed in changeLevel?
        screenState = Screen.REVEAL
        grid.reveal()
        gameTiles = grid.tiles
        totalCoins += currentCoins
        currentCoins = 0
        newCoin = null
    }

    // can this be consolidated with resetGame()?
    fun clearGame() {
        screenState = if (shouldAdvance) {
            currentLevel = if (winningStreak >= 5) 7 else (currentLevel + 1).coerceAtMost(7)
            shouldAdvance = false
            Screen.ADVANCING
        } else {
            Screen.PRE_GAME
        }
        currentPosition = -2 to -2
        isMemoOpen = false
        newCoin = null

        maxLevelCoins = grid.reset(currentLevel, toEmpty = true)
        gameTiles = grid.tiles
        infoSummary = grid.getSummary(isEmpty = true)

        isFirstDouble = true
        isFirstTriple = true
    }

    fun resetGame() {
        screenState = Screen.IN_GAME
        currentPosition = 0 to 0
        isMemoOpen = false
        newCoin = null

        maxLevelCoins = grid.reset(currentLevel)
        gameTiles = grid.tiles
        infoSummary = grid.getSummary()
    }

    private fun changeLevel(isAWin: Boolean) {
        if (isAWin) {
            winningStreak++

        } else {
            winningStreak = 0
            currentLevel = if (grid.numOfFlippedTiles < currentLevel + 1) {
                grid.numOfFlippedTiles.coerceAtLeast(0)
            } else currentLevel--
        }
        totalCoins += currentCoins
        currentCoins = 0
        screenState = Screen.REVEAL
        grid.reveal()
        gameTiles = grid.tiles
    }
}