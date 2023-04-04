package dev.bogwalk.ui.components.buttons

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.onClick
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import dev.bogwalk.ui.style.*
import dev.bogwalk.ui.util.drawInFocusBorders
import dev.bogwalk.ui.util.drawLineBorder

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuitButton(
    currentPosition: Pair<Int, Int>,
    isMemoOpen: Boolean,
    onQuitRequest: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .semantics(mergeDescendants = true) {
                role = Role.Button
                if (currentPosition == -2 to -2) disabled()  // in info screen
            }
            .padding(horizontal = 2.dp, vertical = 15.dp)
            .requiredSize(92.dp, 38.dp)
            .background(Brush.verticalGradient(
                .45f to lightBlue1, .55f to lightBlue2, .6f to lightBlue3
            ))
            .drawBehind {
                if (currentPosition == -1 to -1) {
                    drawInFocusBorders(isMemoOpen)
                } else {
                    drawLineBorder(outerColor = darkGrey)
                    drawLineBorder(innerColor = lightBlue3)
                }
            }
            .onClick(enabled = currentPosition != -2 to -2) { onQuitRequest() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Quit",
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Preview
@Composable
private fun QuitButtonPreview() {
    VoltorbFlipTheme {
        Column {
            QuitButton(currentPosition = 0 to 0, isMemoOpen = false) {}
            QuitButton(currentPosition = -1 to -1, isMemoOpen = false) {}
            QuitButton(currentPosition = -1 to -1, isMemoOpen = true) {}
        }
    }
}