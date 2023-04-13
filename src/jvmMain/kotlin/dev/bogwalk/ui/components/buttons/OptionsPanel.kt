package dev.bogwalk.ui.components.buttons

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import dev.bogwalk.ui.Screen
import dev.bogwalk.ui.style.*
import dev.bogwalk.ui.util.drawBorder
import dev.bogwalk.ui.util.drawLineBorder

@Composable
fun OptionsPanel(
    screen: Screen,
    onPlayRequest: () -> Unit,
    onQuitRequest: () -> Unit,
    onChangeScreen: (Int) -> Unit
) {
    val mainOptions = listOf(PLAY, INFO, QUIT)
    val infoOptions = listOf(HOW_TO, HINT, ABOUT, RETURN)

    // should buttons be highlighted on mouse hover or on focus change?

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
        if (screen == Screen.PRE_GAME) {
            for ((i, option) in mainOptions.withIndex()) {
                key(i) {
                    OptionsButton(
                        text = option,
                        optionNum = i,
                        onActionRequest = {
                            when (it) {
                                0 -> onPlayRequest()
                                1 -> onChangeScreen(Screen.PRE_INFO.ordinal)
                                2 -> onQuitRequest()
                            }
                        }
                    )
                }
            }
        }
        if (screen == Screen.PRE_INFO) {
            for ((i, option) in infoOptions.withIndex()) {
                key(i) {
                    OptionsButton(
                        text = option,
                        optionNum = i,
                        onActionRequest = { onChangeScreen(it) }
                    )
                }
            }
        }
    }
}

@Composable
private fun OptionsButton(
    text: String,
    optionNum: Int,
    onActionRequest: (Int) -> Unit
) {
    var isInFocus by remember { mutableStateOf(false) }

    Text(
        text = text,
        modifier = Modifier
            .semantics { testTag = OPTIONS_TAG }
            .fillMaxWidth()
            .padding(horizontal = 2.dp)
            .requiredHeight(35.dp)
            .background(Color(0xff78c0b0))
            .drawBehind {
                drawLineBorder(outerColor = if (isInFocus) brightRed else offWhite)
                drawLineBorder(innerColor = darkGrey)
            }
            .wrapContentHeight(Alignment.CenterVertically)
            .onFocusChanged { isInFocus = it.isFocused }
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,  // this prevents mouse hover effect
                role = Role.Button
            ) { onActionRequest(optionNum) },
        style = MaterialTheme.typography.labelLarge
    )
}

@Preview
@Composable
private fun OptionsPanelPreview() {
    VoltorbFlipTheme {
        Column {
            OptionsPanel(Screen.PRE_GAME, {}, {}, {})
            OptionsPanel(Screen.PRE_INFO, {}, {}, {})
        }
    }
}