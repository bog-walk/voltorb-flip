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
    var screenState by mutableStateOf(Screen.PRE_GAME)
    var totalCoins by mutableStateOf(0)
    var currentCoins by mutableStateOf(0)
    var newCoin: Int? by mutableStateOf(null)

    var gameTiles by mutableStateOf(grid.tiles)
    var infoSummary by mutableStateOf(grid.getSummary(isEmpty = true))
    var currentPosition by mutableStateOf(-2 to -2)
    var isMemoOpen by mutableStateOf(false)

    private var maxLevelCoins = 0
    private var winningStreak = 0
    private var shouldAdvance = false

    fun selectATile(position: Pair<Int, Int>) {
        if (!isMemoOpen) {
            when (val v = grid.select(position)) {
                0 -> {
                    screenState = Screen.REVEAL
                    currentCoins = 0
                    newCoin = 0
                    winningStreak = 0
                    shouldAdvance = false
                }
                1 -> if (currentCoins == 0) currentCoins = 1
                2, 3 -> {
                    // multiplier speech box displayed every time these tiles are selected
                    screenState = Screen.REVEAL
                    currentCoins = if (currentCoins == 0) v else currentCoins * v
                    newCoin = v
                }
            }
            if (currentCoins == maxLevelCoins) {
                screenState = Screen.GAME_WON
                newCoin = null // game won takes precedence
                winningStreak++
                shouldAdvance = true
            }
        }
        gameTiles = grid.tiles
        currentPosition = position
    }

    fun editCurrentTile(memoToEdit: Int) {
        grid.edit(currentPosition, memoToEdit)
        gameTiles = grid.tiles
    }

    /**
     * Assigns new level data into a new grid and enables screen for a new game.
     *
     * This should only be callable from a screen state that has a game grid cleared of data.
     */
    fun resetGame() {
        screenState = Screen.IN_GAME
        newCoin = null
        currentPosition = 0 to 0
        isMemoOpen = false

        maxLevelCoins = grid.reset(currentLevel)
        gameTiles = grid.tiles
        infoSummary = grid.getSummary()
    }

    /**
     * Ends current game round by flipping over any unflipped tiles and moving all gained coins
     * to the overall total.
     */
    fun endGame() {
        screenState = Screen.REVEAL
        totalCoins += currentCoins
        currentCoins = 0
        newCoin = null

        grid.reveal()
        gameTiles = grid.tiles
    }

    /**
     * Sets up intermediate screen after a round is lost/won and revealed, by generating a disabled
     * grid without any level data.
     */
    fun clearGame() {
        screenState = advanceOrDrop()
        newCoin = null
        currentPosition = -2 to -2
        isMemoOpen = false

        maxLevelCoins = grid.reset(currentLevel, toEmpty = true)
        gameTiles = grid.tiles
        infoSummary = grid.getSummary(isEmpty = true)
    }

    /**
     * Winning a round causes level to advance by 1 (or jump to level 7 if 5 games won in a row).
     * Losing a round causes level to drop by 1, unless fewer tiles were flipped than the current
     * level number. In the latter case, the level is dropped to the amount of tiles flipped.
     *
     * If a game is lost while on level 1, for example, no speech box text will display as level
     * remains unchanged.
     */
    private fun advanceOrDrop(): Screen {
        return if (shouldAdvance) {
            currentLevel = if (winningStreak >= 5) 8 else (currentLevel + 1).coerceAtMost(8)
            shouldAdvance = false
            Screen.ADVANCING
        } else {
            val prevLevel = currentLevel
            currentLevel = if (grid.numOfFlippedTiles < currentLevel) {
                grid.numOfFlippedTiles.coerceAtLeast(1)
            } else (currentLevel - 1).coerceAtLeast(1)
            shouldAdvance = true
            if (currentLevel < prevLevel) Screen.DROPPING else Screen.PRE_GAME
        }
    }
}