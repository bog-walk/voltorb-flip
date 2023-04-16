package dev.bogwalk.ui.components.buttons

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.ExperimentalTextApi
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

    Column(
        modifier = Modifier
            .requiredWidth(190.dp)
            .padding(end = standardPadding)
            .background(darkGrey)
            .drawBehind {
                drawBorder(thinBorder.toPx(), 0f, darkGrey, StrokeCap.Butt)
                drawBorder(thinBorder.toPx(), 3.1.dp.toPx(), lightGrey4)
            }
            .padding(horizontal = thickerPadding, vertical = 12.dp)
            .background(disabledBlue3),
        verticalArrangement = Arrangement.spacedBy(thickBorder * 2)
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
        } else if (screen == Screen.PRE_INFO) {
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

@OptIn(ExperimentalTextApi::class, ExperimentalComposeUiApi::class)
@Composable
private fun OptionsButton(
    text: String,
    optionNum: Int,
    onActionRequest: (Int) -> Unit
) {
    var isInFocus by remember { mutableStateOf(false) }

    Box(
        Modifier
            .testTag(OPTIONS_TAG)
            .fillMaxWidth()
            .padding(horizontal = thinBorder)
            .requiredHeight(35.dp)
            .background(blueGreen)
            .drawBehind {
                drawLineBorder(outerColor = if (isInFocus) brightRed else offWhite)
                drawLineBorder(innerColor = darkGrey)
            }
            .wrapContentHeight(Alignment.CenterVertically)
            .onPointerEvent(PointerEventType.Enter) { isInFocus = true }
            .onPointerEvent(PointerEventType.Exit) { isInFocus = false }
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,  // this prevents mouse hover effect
                role = Role.Button
            ) { onActionRequest(optionNum) },
        contentAlignment = Alignment.Center
    ) {
        // drawStyle removes fill colour, so only option for outlined text?
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge.copy(
                color = offWhite,
                drawStyle = null
            )
        )
    }
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