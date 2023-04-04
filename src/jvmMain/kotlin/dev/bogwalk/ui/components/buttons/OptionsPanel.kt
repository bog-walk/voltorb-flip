package dev.bogwalk.ui.components.buttons

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.onClick
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import dev.bogwalk.ui.style.*
import dev.bogwalk.ui.util.drawBorder
import dev.bogwalk.ui.util.drawLineBorder

@Composable
fun OptionsPanel(
    onPlayRequest: () -> Unit,
    onQuitRequest: () -> Unit,
    onInfoRequest: (Int) -> Unit
) {
    val mainOptions = listOf("Play", "Game Info", "Quit")
    val infoOptions = listOf("How to Play", "Hint!", "About Memos", "Return")

    var showInfoOptions by mutableStateOf(false)
    var optionInFocus by mutableStateOf(0)

    Box(
        Modifier
            .requiredWidth(190.dp)
            .padding(5.dp)
            .background(darkGrey)
            .drawBehind {
                drawBorder(2.dp.toPx(), 0f, darkGrey, StrokeCap.Butt)
                drawBorder(2.dp.toPx(), 3.1.dp.toPx(), Color(0xff888888))
            }
        ,
        contentAlignment = Alignment.Center
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 12.dp)
                .background(disabledBlue3),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (showInfoOptions) {
                for ((i, option) in infoOptions.withIndex()) {
                    key(i) {
                        OptionsButton(option,
                            optionNum = if (i == 3) null else i,
                            isInFocus = optionInFocus == i,
                            onActionRequest = { showInfoOptions = false },
                            onInfoRequest = onInfoRequest)
                    }
                }
            } else {
                for ((i, option) in mainOptions.withIndex()) {
                    key(i) {
                        OptionsButton(option, null,
                            isInFocus = optionInFocus == i,
                            onActionRequest = {
                                optionInFocus = i
                                when (i) {
                                    0 -> onPlayRequest()
                                    1 -> showInfoOptions = true
                                    2 -> onQuitRequest()
                                }
                            })
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun OptionsButton(
    text: String,
    optionNum: Int?,
    isInFocus: Boolean,
    onActionRequest: () -> Unit = {},
    onInfoRequest: (Int) -> Unit = {}
) {
    Box(
        Modifier
            .semantics(mergeDescendants = true) {
                role = Role.Button
            }
            .fillMaxWidth()
            .padding(horizontal = 2.dp)
            .requiredHeight(35.dp)
            .background(Color(0xff78c0b0))
            .drawBehind {
                drawLineBorder(outerColor = if (isInFocus) brightRed else offWhite)
                drawLineBorder(innerColor = darkGrey)
            }
            .onClick { if (optionNum == null) onActionRequest() else onInfoRequest(optionNum) },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Preview
@Composable
private fun OptionsPanelPreview() {
    VoltorbFlipTheme {
        OptionsPanel({}, {}, {})
    }
}