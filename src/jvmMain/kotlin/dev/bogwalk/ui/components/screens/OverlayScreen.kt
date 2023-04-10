package dev.bogwalk.ui.components.screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
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
        SpeechBox(when (screen) {
            Screen.ABOUT_GAME -> "Info about game..."
            Screen.ABOUT_HINT -> "Here's a hint..."
            Screen.ABOUT_MEMO -> "Info about memo..."
            Screen.PRE_INFO -> "Which set of info?\n"
            Screen.PRE_GAME -> "Play VOLTORB Flip Lv. 1?\n"
            Screen.IN_GAME -> ""
            Screen.QUITTING -> "You haven't found any Coins!\n" +
                    "Are you sure you want to quit?"
        })
    }
}

@Composable
private fun SpeechBox(
    text: String
) {
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
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            modifier = Modifier
                .fillMaxWidth(.9f)
                .padding(horizontal = 15.dp, vertical = 10.dp)
                .background(Color.White, RoundedCornerShape(2.dp))
                .padding(horizontal = 10.dp, vertical = 5.dp),
            maxLines = 2,
            style = MaterialTheme.typography.bodyMedium
        )
        if (false) {  // this should be dependent on length of provided string (list of?)
            IconButton(
                onClick = {}
            ) {
                Icon(
                    painter = painterResource(INFO_ARROW),
                    contentDescription = INFO_ARROW_DESCR,
                    modifier = Modifier.requiredSize(30.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun SpeechBoxPreview() {
    VoltorbFlipTheme {
        Column {
            SpeechBox("Play VOLTORB Flip Lv. 1?\n")
            SpeechBox("Here is an example of some long text that should break")
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