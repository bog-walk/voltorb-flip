package dev.bogwalk.ui.components.buttons

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.unit.dp
import dev.bogwalk.ui.Screen
import dev.bogwalk.ui.style.*
import dev.bogwalk.ui.util.drawInFocusBorders
import dev.bogwalk.ui.util.drawLineBorder

@OptIn(ExperimentalTextApi::class)
@Composable
fun QuitButton(
    screen: Screen,
    currentPosition: Pair<Int, Int>,
    isMemoOpen: Boolean,
    onQuitRequest: () -> Unit = {}
) {
    Box(
        Modifier
            .semantics(mergeDescendants = true) {
                if (screen != Screen.IN_GAME) disabled()
            }
            .padding(vertical = 15.dp)
            .requiredSize(95.dp, 38.dp)
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
            .wrapContentHeight(Alignment.CenterVertically)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,  // this prevents mouse hover effect
                enabled = screen == Screen.IN_GAME,
                role = Role.Button
            ) { onQuitRequest() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = QUIT,
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            text = QUIT,
            style = MaterialTheme.typography.labelLarge.copy(
                color = offWhite,
                drawStyle = null
            )
        )
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun QuitOption(
    option: String,
    onSelect: () -> Unit = {}
) {
    var isInFocus by remember { mutableStateOf(false) }
    Text(
        text = option,
        modifier = Modifier
            .padding(4.dp)
            .requiredSize(80.dp, 50.dp)
            .background(Brush.verticalGradient(
                .50f to offWhite, .55f to lightGrey1, .6f to lightGrey2
            ), RoundedCornerShape(6.dp))
            .drawBehind {
                drawRoundRect(
                    if (isInFocus) brightRed else darkGrey,
                    Offset(5.dp.toPx(), 5.dp.toPx()),
                    Size(size.width - 10.dp.toPx(), size.height - 10.dp.toPx()),
                    CornerRadius(4.dp.toPx()),
                    Stroke(4.dp.toPx())
                )
            }
            .wrapContentHeight(Alignment.CenterVertically)
            .onFocusChanged { isInFocus = it.isFocused }
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,  // this prevents mouse hover effect
                role = Role.Button
            ) { onSelect() },
        style = MaterialTheme.typography.labelLarge.copy(
            drawStyle = Stroke(width = 1.5f, join = StrokeJoin.Round)
        )
    )
}

@Preview
@Composable
private fun QuitButtonPreview() {
    VoltorbFlipTheme {
        Column {
            QuitButton(Screen.IN_GAME, currentPosition = 0 to 0, isMemoOpen = false) {}
            QuitButton(Screen.IN_GAME, currentPosition = -1 to -1, isMemoOpen = false) {}
            QuitButton(Screen.IN_GAME, currentPosition = -1 to -1, isMemoOpen = true) {}
        }
    }
}

@Preview
@Composable
private fun QuitOptionsPreview() {
    VoltorbFlipTheme {
        Column {
            QuitOption(YES) {}
            QuitOption(NO) {}
        }
    }
}