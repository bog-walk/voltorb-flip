package dev.bogwalk.ui.components.screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import dev.bogwalk.ui.Screen
import dev.bogwalk.ui.components.buttons.OptionsPanel
import dev.bogwalk.ui.components.buttons.QuitOption
import dev.bogwalk.ui.style.*

@Composable
fun OverlayScreen(
    screen: Screen,
    level: Int,
    coins: Int,
    onPlayRequest: () -> Unit,
    onClearRequest: () -> Unit,
    onQuitRequest: () -> Unit,
    onContinueRequest: () -> Unit,
    onRevealRequest: () -> Unit,
    onChangeScreen: (Int) -> Unit,
    onLastLineNext: () -> Unit,
    newCoin: Int? = null,
) {
    Column(
        modifier = Modifier
            .testTag(OVERLAY_TAG)
            .fillMaxSize()
            .background(
                if (screen == Screen.REVEAL) Color.Transparent else Color(0f,0f,0f,.3f)
            )
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,  // this prevents mouse hover effect
                enabled = screen == Screen.REVEAL || screen == Screen.DROPPING)
            { when (newCoin) {
                null -> if (screen == Screen.REVEAL) onClearRequest() else onContinueRequest()
                2, 3 -> onContinueRequest()
                0 -> onRevealRequest()
            }},
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ) {
        when (screen) {
            Screen.ABOUT_GAME -> SpeechBox(HOW_TO_TEXT, { onChangeScreen(Screen.PRE_INFO.ordinal) })
            Screen.ABOUT_HINT -> SpeechBox(HINT_TEXT, { onChangeScreen(Screen.PRE_INFO.ordinal) })
            Screen.ABOUT_MEMO -> SpeechBox(ABOUT_TEXT, { onChangeScreen(Screen.PRE_INFO.ordinal) })
            Screen.PRE_GAME -> {
                OptionsPanel(screen, onPlayRequest, onQuitRequest, onChangeScreen)
                SpeechBox("$START_GAME_START$level$START_GAME_END")
            }
            Screen.PRE_INFO -> {
                OptionsPanel(screen, onPlayRequest, onQuitRequest, onChangeScreen)
                SpeechBox(REQUEST_INFO)
            }
            Screen.QUITTING -> {
                var onlastLine by remember { mutableStateOf(coins == 0) }

                if (onlastLine) {
                    QuitOption(YES) { onRevealRequest() }
                    QuitOption(NO) { onContinueRequest() }
                }
                // only choosing 1 of 2 options should allow this screen to exit
                SpeechBox(
                    text = if (coins == 0) QUIT_NO_COINS else "$QUIT_START$coins$QUIT_END",
                    onLastLineNext = { onlastLine = true }
                )
            }
            Screen.REVEAL -> {
                newCoin?.let { SpeechBox(
                    if (it == 0) NO_COINS else "$NEW_1$it$NEW_2$it$NEW_3")
                }
            }
            Screen.GAME_WON -> {
                SpeechBox(text = "$GAME_CLEAR_START$coins$GAME_CLEAR_END",
                    onFinish = onRevealRequest, onLastLineNext = onLastLineNext)
            }
            Screen.ADVANCING -> {
                SpeechBox("$ADVANCE_START$level$ADVANCE_END", onFinish = onContinueRequest)
            }
            Screen.DROPPING -> {
                SpeechBox("$DROPPED_START$level$DROPPED_END")
            }
            else -> {}
        }
    }
}

@Preview
@Composable
private fun OverlayScreenPreGamePreview() {
    VoltorbFlipTheme {
        BottomScreen(Modifier.requiredSize(windowWidth / 2, windowHeight)) {
            OverlayScreen(Screen.PRE_GAME, 1, 0, {}, {}, {}, {}, {}, {}, {})
        }
    }
}

@Preview
@Composable
private fun OverlayScreenPreInfoPreview() {
    VoltorbFlipTheme {
        BottomScreen(Modifier.requiredSize(windowWidth / 2, windowHeight)) {
            OverlayScreen(Screen.PRE_INFO, 1, 0, {}, {}, {}, {}, {}, {}, {})
        }
    }
}

@Preview
@Composable
private fun OverlayScreenQuittingPreview() {
    VoltorbFlipTheme {
        BottomScreen(Modifier.requiredSize(windowWidth / 2, windowHeight)) {
            OverlayScreen(Screen.QUITTING, 1, 6, {}, {}, {}, {}, {}, {}, {})
        }
    }
}

@Preview
@Composable
private fun OverlayScreenQuittingWithNoCoinsPreview() {
    VoltorbFlipTheme {
        BottomScreen(Modifier.requiredSize(windowWidth / 2, windowHeight)) {
            OverlayScreen(Screen.QUITTING, 1, 0,  {}, {}, {}, {}, {}, {}, {})
        }
    }
}

@Preview
@Composable
private fun OverlayScreenInInfoPreview() {
    VoltorbFlipTheme {
        BottomScreen(Modifier.requiredSize(windowWidth / 2, windowHeight)) {
            OverlayScreen(Screen.ABOUT_GAME, 1, 0, {}, {}, {}, {}, {}, {}, {})
        }
    }
}