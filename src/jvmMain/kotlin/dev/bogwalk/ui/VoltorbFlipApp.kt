package dev.bogwalk.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
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
    Row(
        modifier = Modifier.height(IntrinsicSize.Max),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TopScreen(
            Modifier.weight(.49f)
        ) {
            when (state.screenState) {
                Screen.ABOUT_GAME -> HowToPlayScreen()
                Screen.ABOUT_HINT -> HintScreen()
                Screen.ABOUT_MEMO -> AboutMemoScreen()
                else -> InfoScreen(state.currentLevel, state.totalCoins, state.currentCoins)
            }
        }
        Box(
            Modifier.weight(.51f).height(IntrinsicSize.Max)
        ) {
            GameScreen(
                state.screenState,
                state.grid,
                state.infoGrid,
                state.currentPosition,
                state.isMemoOpen,
                state::selectATile,
                { state.isMemoOpen = !state.isMemoOpen },
                state::editCurrentTile
            ) {
                state.currentPosition = -1 to -1
                state.screenState = Screen.QUITTING
            }
            if (state.screenState != Screen.IN_GAME) {
                OverlayScreen(
                    state.screenState,
                    state::resetGame,
                    state::clearGame,
                    state::endGame
                ) {
                    state.screenState = Screen.values()[it]
                }
            }
        }
    }
}

@Preview
@Composable
private fun AppPreview() {
    VoltorbFlipTheme {
        Box(Modifier.requiredWidth(900.dp)) {
            VoltorbFlipApp(VoltorbFlipAppState())
        }
    }
}