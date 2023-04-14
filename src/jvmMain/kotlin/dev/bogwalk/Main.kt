package dev.bogwalk

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import dev.bogwalk.ui.Screen
import dev.bogwalk.ui.VoltorbFlipApp
import dev.bogwalk.ui.style.VoltorbFlipTheme
import dev.bogwalk.ui.util.VoltorbFlipAppState

fun main() = application {
    val gameState by remember { mutableStateOf(VoltorbFlipAppState()) }

    if (gameState.screenState == Screen.POST_GAME) { exitApplication() }

    Window(
        onCloseRequest = ::exitApplication,
        state = rememberWindowState(
            position = WindowPosition(Alignment.Center),
            width = 920.dp, height = 370.dp
        ),
        undecorated = true,  // window no longer moveable by user?
        resizable = false
    ) {
        VoltorbFlipTheme {
            VoltorbFlipApp(gameState)
        }
    }
}