package dev.bogwalk.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.bogwalk.ui.components.screens.*
import dev.bogwalk.ui.style.VoltorbFlipTheme
import dev.bogwalk.ui.style.thickerPadding
import dev.bogwalk.ui.style.windowHeight
import dev.bogwalk.ui.style.windowWidth
import dev.bogwalk.ui.util.VoltorbFlipAppState

enum class Screen {
    ABOUT_GAME, ABOUT_HINT, ABOUT_MEMO,
    PRE_GAME, PRE_INFO, IN_GAME, QUITTING,
    REVEAL, POST_GAME, GAME_WON, ADVANCING, DROPPING
}

@Composable
@Preview
fun VoltorbFlipApp(state: VoltorbFlipAppState) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(thickerPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TopScreen(
            Modifier.weight(.49f)
        ) {
            when (state.screenState) {
                Screen.ABOUT_GAME -> HowToPlayScreen()
                Screen.ABOUT_HINT -> HintScreen()
                Screen.ABOUT_MEMO -> AboutMemoScreen()
                Screen.POST_GAME -> ThanksTopScreen()
                else -> InfoScreen(state.currentLevel, state.totalCoins, state.currentCoins)
            }
        }
        BottomScreen(
            Modifier.weight(.51f)
        ) {
            if (state.screenState == Screen.POST_GAME) {
                ThanksBottomScreen()
            } else {
                GameScreen(
                    screen = state.screenState,
                    grid = state.gameTiles,
                    infoGrid = state.infoSummary,
                    currentPosition = state.currentPosition,
                    isMemoOpen = state.isMemoOpen,
                    onSelectRequest = state::selectATile,
                    onMemoRequest = { state.isMemoOpen = !state.isMemoOpen },
                    onEditMemoRequest = state::editCurrentTile,
                    onQuitRequest = {
                        state.screenState = Screen.QUITTING
                        state.currentPosition = -1 to -1
                        state.isMemoOpen = false
                    }
                )
                if (state.screenState != Screen.IN_GAME) {
                    OverlayScreen(
                        screen = state.screenState,
                        level = state.currentLevel,
                        coins = state.currentCoins,
                        onPlayRequest = state::resetGame,
                        onClearRequest = state::clearGame,
                        onQuitRequest = { state.screenState = Screen.POST_GAME },
                        onContinueRequest = {
                            state.newCoin = null
                            state.screenState = if (state.screenState in listOf(
                                    Screen.ADVANCING, Screen.DROPPING)) {
                                Screen.PRE_GAME
                            } else { Screen.IN_GAME }
                        },
                        onRevealRequest = state::endGame,
                        onChangeScreen = { state.screenState = Screen.values()[it] },
                        onLastLineNext = {  // should only be invoked when game won
                            state.totalCoins += state.currentCoins
                            state.currentCoins = 0
                        },
                        newCoin = state.newCoin
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun AppPreview() {
    VoltorbFlipTheme {
        Box(Modifier.requiredSize(windowWidth, windowHeight)) {
            VoltorbFlipApp(VoltorbFlipAppState())
        }
    }
}