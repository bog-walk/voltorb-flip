package dev.bogwalk.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.bogwalk.ui.components.screens.*
import dev.bogwalk.ui.style.VoltorbFlipTheme
import dev.bogwalk.ui.util.VoltorbFlipAppState

enum class Screen {
    ABOUT_GAME, ABOUT_HINT, ABOUT_MEMO, PRE_GAME, PRE_INFO, IN_GAME, QUITTING, REVEAL, POST_GAME
}

@Composable
@Preview
fun VoltorbFlipApp(state: VoltorbFlipAppState) {
    Row(  // possible to center composable within a window scope? must use Box?
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
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
                    }
                )
                if (state.screenState != Screen.IN_GAME) {
                    OverlayScreen(
                        screen = state.screenState,
                        onPlayRequest = state::resetGame,
                        onClearRequest = state::clearGame,
                        onQuitRequest = { state.screenState = Screen.POST_GAME },
                        onContinueRequest = { state.screenState = Screen.IN_GAME },
                        onRevealRequest = state::endGame,
                        onChangeScreen = { state.screenState = Screen.values()[it] }
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
        Box(Modifier.requiredSize(920.dp, 370.dp)) {
            VoltorbFlipApp(VoltorbFlipAppState())
        }
    }
}