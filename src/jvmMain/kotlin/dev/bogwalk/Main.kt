package dev.bogwalk

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.bogwalk.ui.VoltorbFlipApp

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        resizable = false
    ) {
        VoltorbFlipApp()
    }
}