package dev.bogwalk

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
        state = WindowState(width = 900.dp, height = 450.dp),
        resizable = false
    ) {
        VoltorbFlipTheme {
            VoltorbFlipApp(gameState)
        }
    }
}