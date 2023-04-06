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
import androidx.compose.ui.semantics.testTag
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
    val mainOptions = listOf(PLAY, INFO, QUIT)
    val infoOptions = listOf(HOW_TO, HINT, ABOUT, RETURN)

    var showInfoOptions by remember { mutableStateOf(false) }
    var optionInFocus by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .requiredWidth(190.dp)
            .padding(end = 6.dp)
            .background(darkGrey)
            .drawBehind {
                drawBorder(2.dp.toPx(), 0f, darkGrey, StrokeCap.Butt)
                drawBorder(2.dp.toPx(), 3.1.dp.toPx(), Color(0xff888888))
            }
            .padding(horizontal = 10.dp, vertical = 12.dp)
            .background(disabledBlue3),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (showInfoOptions) {
            for ((i, option) in infoOptions.withIndex()) {
                key(i) {
                    OptionsButton(
                        text = option,
                        optionNum = i,
                        isInInfo = showInfoOptions,
                        isInFocus = optionInFocus == i,
                        onActionRequest = {
                            optionInFocus = 0
                            showInfoOptions = false
                        },
                        onInfoRequest = {
                            optionInFocus = it
                            onInfoRequest(it)
                        }
                    )
                }
            }
        } else {
            for ((i, option) in mainOptions.withIndex()) {
                key(i) {
                    OptionsButton(
                        text = option,
                        optionNum = i,
                        isInInfo = showInfoOptions,
                        isInFocus = optionInFocus == i,
                        onActionRequest = {
                            when (it) {
                                0 -> {
                                    optionInFocus = 0
                                    onPlayRequest()
                                }
                                1 -> {
                                    optionInFocus = 0
                                    showInfoOptions = true
                                }
                                2 -> {
                                    optionInFocus = 2
                                    onQuitRequest()
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun OptionsButton(
    text: String,
    optionNum: Int,
    isInInfo: Boolean,
    isInFocus: Boolean,
    onActionRequest: (Int) -> Unit = {},
    onInfoRequest: (Int) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .semantics(mergeDescendants = true) {
                role = Role.Button
                testTag = OPTIONS_TAG
            }
            .fillMaxWidth()
            .padding(horizontal = 2.dp)
            .requiredHeight(35.dp)
            .background(Color(0xff78c0b0))
            .drawBehind {
                drawLineBorder(outerColor = if (isInFocus) brightRed else offWhite)
                drawLineBorder(innerColor = darkGrey)
            }
            .onClick { if (isInInfo) onInfoRequest(optionNum) else onActionRequest(optionNum) },
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