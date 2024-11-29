package dev.bogwalk

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import dev.bogwalk.common.generated.resources.Res
import dev.bogwalk.common.generated.resources.zero
import dev.bogwalk.ui.Screen
import dev.bogwalk.ui.VoltorbFlipApp
import dev.bogwalk.ui.style.VoltorbFlipTheme
import dev.bogwalk.ui.style.windowHeight
import dev.bogwalk.ui.style.windowWidth
import dev.bogwalk.ui.util.VoltorbFlipAppState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

@Composable
fun ApplicationScope.VoltorbFlipAppDesktop() {
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
            width = windowWidth, height = windowHeight
        ),
        icon = painterResource(Res.drawable.zero),
        undecorated = true,
        resizable = false
    ) {
        VoltorbFlipTheme {
            VoltorbFlipApp(gameState)
        }
    }
}