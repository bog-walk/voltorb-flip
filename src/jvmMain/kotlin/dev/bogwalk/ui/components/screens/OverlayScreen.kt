package dev.bogwalk.ui.components.screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.bogwalk.ui.components.buttons.OptionsPanel
import dev.bogwalk.ui.components.buttons.QuitOption
import dev.bogwalk.ui.style.*
import dev.bogwalk.ui.util.Screen
import dev.bogwalk.ui.util.drawLineBorder

@Composable
fun OverlayScreen(
    screen: Screen,
    onPlayRequest: () -> Unit,
    onQuitRequest: () -> Unit,
    onInfoRequest: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0f,0f,0f,.3f)),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ) {
        if (screen == Screen.QUITTING) {
            QuitOption(YES) { onQuitRequest() }
            QuitOption(NO) { onPlayRequest() }
        } else {
            OptionsPanel(onPlayRequest, onQuitRequest, onInfoRequest)
        }
        SpeechBox(
            when (screen) {
                Screen.ABOUT_GAME -> "Info about game..."
                Screen.ABOUT_HINT -> "Here's a hint..."
                Screen.ABOUT_MEMO -> "Info about memo..."
                Screen.PRE_INFO -> REQUEST_INFO
                Screen.PRE_GAME -> START_GAME
                Screen.IN_GAME -> ""
                Screen.QUITTING -> QUIT_GAME
            }
        ) { onInfoRequest(4) }  // only 3 info screens will show next arrow
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
            fontSize = 16.sp,
            style = MaterialTheme.typography.bodyMedium
        )
        if (groupedLines.size > 1) {
            IconButton(
                onClick = {
                    if (group == groupedLines.lastIndex &&
                        line + 1 == groupedLines[group].lastIndex) {
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
                    modifier = Modifier.requiredSize(20.dp),
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
        Box(Modifier.requiredSize(450.dp)) {
            Column {
                SpeechBox(START_GAME) {}
                SpeechBox(ABOUT_TEXT) {}
            }
        }
    }
}

@Preview
@Composable
private fun OverlayScreenPreview() {
    VoltorbFlipTheme {
        Box(Modifier.requiredSize(450.dp)) {
            OverlayScreen(Screen.PRE_GAME, {}, {}) {}
        }
    }
}

@Preview
@Composable
private fun OverlayScreenQuitPreview() {
    VoltorbFlipTheme {
        Box(Modifier.requiredSize(450.dp)) {
            OverlayScreen(Screen.QUITTING, {}, {}) {}
        }
    }
}