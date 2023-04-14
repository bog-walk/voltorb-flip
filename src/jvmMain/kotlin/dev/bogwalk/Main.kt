package dev.bogwalk

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import dev.bogwalk.ui.Screen
import dev.bogwalk.ui.VoltorbFlipApp
import dev.bogwalk.ui.style.INFO_ZERO
import dev.bogwalk.ui.style.VoltorbFlipTheme
import dev.bogwalk.ui.util.VoltorbFlipAppState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main() = application {
    val gameState by remember { mutableStateOf(VoltorbFlipAppState()) }
    val windowCoroutineScope = rememberCoroutineScope()

    if (gameState.screenState == Screen.POST_GAME) {
        windowCoroutineScope.launch {
            delay(2000)
            exitApplication()
        }
    }

    Window(
        onCloseRequest = ::exitApplication,
        state = rememberWindowState(
            position = WindowPosition(Alignment.Center),
            width = 920.dp, height = 370.dp
        ),
        icon = painterResource(INFO_ZERO),
        undecorated = true,  // window no longer moveable by user?
        resizable = false
    ) {
        VoltorbFlipTheme {
            VoltorbFlipApp(gameState)
        }
    }
}