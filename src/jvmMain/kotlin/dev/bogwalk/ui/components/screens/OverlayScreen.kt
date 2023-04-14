package dev.bogwalk.ui.components.screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.bogwalk.ui.Screen
import dev.bogwalk.ui.components.buttons.OptionsPanel
import dev.bogwalk.ui.components.buttons.QuitOption
import dev.bogwalk.ui.style.*
import dev.bogwalk.ui.util.drawLineBorder

@Composable
fun OverlayScreen(
    screen: Screen,
    onPlayRequest: () -> Unit,
    onClearRequest: () -> Unit,
    onQuitRequest: () -> Unit,
    onContinueRequest: () -> Unit,
    onRevealRequest: () -> Unit,
    onChangeScreen: (Int) -> Unit
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
                enabled = screen == Screen.REVEAL)
            { onClearRequest() },
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ) {
        when (screen) {
            Screen.ABOUT_GAME -> SpeechBox(HOW_TO_TEXT) { onChangeScreen(Screen.PRE_INFO.ordinal) }
            Screen.ABOUT_HINT -> SpeechBox(HINT_TEXT) { onChangeScreen(Screen.PRE_INFO.ordinal) }
            Screen.ABOUT_MEMO -> SpeechBox(ABOUT_TEXT) { onChangeScreen(Screen.PRE_INFO.ordinal) }
            Screen.PRE_GAME -> {
                OptionsPanel(screen, onPlayRequest, onQuitRequest, onChangeScreen)
                SpeechBox(START_GAME) {}
            }
            Screen.PRE_INFO -> {
                OptionsPanel(screen, onPlayRequest, onQuitRequest, onChangeScreen)
                SpeechBox(REQUEST_INFO) {}
            }
            Screen.QUITTING -> {
                QuitOption(YES) { onRevealRequest() }
                QuitOption(NO) { onContinueRequest() }
                SpeechBox(QUIT_GAME) {}
            }
            else -> {}
        }
    }
}

@Composable
private fun SpeechBox(
    text: String,
    onFinish: () -> Unit
) {
    val groupedLines = text.split("|").map { it.split("\n") }
    var group by remember { mutableStateOf(0) }
    var line by remember { mutableStateOf(0) }

    Row(
        modifier = Modifier
            .testTag(SPEECH_TAG)
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .padding(horizontal = 8.dp, vertical = 10.dp)
            .background(
                Brush.verticalGradient(
                    .7f to Color(0xff63636b), .8f to Color(0xff5a5a63),
                    .85f to Color(0xff52525a)
                )
            )
            .drawBehind {
                // are these colours correct?
                drawLineBorder(outerColor = darkGrey)
                drawLineBorder(innerColor = Color(0xffffde6b))
            },
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = "${groupedLines[group][line]}\n${groupedLines[group][line + 1]}",
            modifier = Modifier
                .fillMaxWidth(.9f)
                .padding(start = 15.dp, top = 10.dp, end = 0.dp, bottom = 10.dp)
                .background(Color.White, RoundedCornerShape(2.dp))
                .padding(5.dp)
                .align(Alignment.CenterVertically),
            maxLines = 2,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp)
        )
        if (groupedLines.size > 1) {
            IconButton(
                onClick = {
                    if (group == groupedLines.lastIndex &&
                        line + 1 == groupedLines[group].lastIndex) {
                        group = 0
                        line = 0
                        onFinish()
                    } else if (line + 1 == groupedLines[group].lastIndex) {
                        group++
                        line = 0
                    } else  {
                        line++
                    }
                },
                modifier = Modifier.align(Alignment.Bottom)
            ) {
                Icon(
                    painter = painterResource(NEXT_ARROW),
                    contentDescription = NEXT_ARROW_DESCR,
                    modifier = Modifier.requiredSize(16.dp),
                    tint = Color.Unspecified
                )
            }
        }
    }
}

@Preview
@Composable
private fun SpeechBoxPreview() {
    VoltorbFlipTheme {
        BottomScreen(Modifier.requiredSize(450.dp, 360.dp)) {
            SpeechBox(ABOUT_TEXT) {}
            SpeechBox(START_GAME) {}
        }
    }
}

@Preview
@Composable
private fun OverlayScreenPreGamePreview() {
    VoltorbFlipTheme {
        BottomScreen(Modifier.requiredSize(450.dp, 360.dp)) {
            OverlayScreen(Screen.PRE_GAME, {}, {}, {}, {}, {}) {}
        }
    }
}

@Preview
@Composable
private fun OverlayScreenPreInfoPreview() {
    VoltorbFlipTheme {
        BottomScreen(Modifier.requiredSize(450.dp, 360.dp)) {
            OverlayScreen(Screen.PRE_INFO, {}, {}, {}, {}, {}) {}
        }
    }
}

@Preview
@Composable
private fun OverlayScreenQuittingPreview() {
    VoltorbFlipTheme {
        BottomScreen(Modifier.requiredSize(450.dp, 360.dp)) {
            OverlayScreen(Screen.QUITTING, {}, {}, {}, {}, {}) {}
        }
    }
}

@Preview
@Composable
private fun OverlayScreenInInfoPreview() {
    VoltorbFlipTheme {
        BottomScreen(Modifier.requiredSize(450.dp, 360.dp)) {
            OverlayScreen(Screen.ABOUT_GAME, {}, {}, {}, {}, {}) {}
        }
    }
}