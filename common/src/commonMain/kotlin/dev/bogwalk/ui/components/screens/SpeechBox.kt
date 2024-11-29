package dev.bogwalk.ui.components.screens

import androidx.compose.animation.core.*
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.bogwalk.common.generated.resources.Res
import dev.bogwalk.common.generated.resources.next_arrow
import dev.bogwalk.ui.style.*
import dev.bogwalk.ui.util.drawLineBorder
import org.jetbrains.compose.resources.painterResource

@Composable
fun SpeechBox(
    text: String,
    onFinish: () -> Unit = {},
    onLastLineNext: (() -> Unit)? = null
) {
    // remembering this ensures that the correct text is displayed when coins are received
    val groupedLines by remember { mutableStateOf(text.split("|").map { it.split("\n") }) }
    var group by remember { mutableStateOf(0) }
    var line by remember { mutableStateOf(0) }

    Row(
        modifier = Modifier
            .testTag(SPEECH_TAG)
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = thickerPadding)
            .background(
                Brush.verticalGradient(
                    .7f to Color(0xff63636b), .8f to Color(0xff5a5a63),
                    .85f to Color(0xff52525a)
                )
            )
            .drawBehind {
                drawLineBorder(outerColor = darkGrey)
                drawLineBorder(innerColor = Color(0xffffde6b))
            },
        horizontalArrangement = Arrangement.Start
    ) {
        // technically this should hold a typewriter effect animation
        Text(
            text = groupedLines[group][line] +
                    (if (groupedLines[group].size > 1) "\n" else "") +
                    (groupedLines[group].getOrNull(line + 1) ?: ""),
            modifier = Modifier
                .fillMaxWidth(.9f)
                .padding(start = 15.dp, top = thickerPadding, end = 0.dp, bottom = thickerPadding)
                .background(Color.White, RoundedCornerShape(thinBorder))
                .padding(thickerPadding / 2)
                .align(Alignment.CenterVertically),
            maxLines = 2,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp)
        )
        if (groupedLines.size > 1 || groupedLines.single().size > 2) {
            val infiniteBounce = rememberInfiniteTransition()
            val yOffset by infiniteBounce.animateValue(
                initialValue = 0.dp,
                targetValue = (-8).dp,
                typeConverter = Dp.VectorConverter,
                animationSpec = infiniteRepeatable(
                    animation = tween(600, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )
            IconButton(
                onClick = {
                    if (group == groupedLines.lastIndex &&
                        line + 2 == groupedLines[group].lastIndex) {
                        line++
                        onLastLineNext?.invoke()
                    } else if (group == groupedLines.lastIndex &&
                        line + 1 == groupedLines[group].lastIndex) {
                        if (onLastLineNext == null) {
                            group = 0
                            line = 0
                        }
                        onFinish()
                    } else if (line + 1 == groupedLines[group].lastIndex) {
                        group++
                        line = 0
                    } else  {
                        line++
                    }
                    if (group == groupedLines.lastIndex &&
                        line + 1 == groupedLines[group].lastIndex) {
                        onLastLineNext?.invoke()
                    }
                },
                modifier = Modifier.align(Alignment.Bottom)
            ) {
                Icon(
                    painter = painterResource(Res.drawable.next_arrow),
                    contentDescription = NEXT_ARROW_DESCR,
                    modifier = Modifier.requiredSize(memoIconSize).offset(x = 0.dp, y = yOffset),
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
        BottomScreen(Modifier.requiredSize(windowWidth / 2, windowHeight)) {
            Column {
                SpeechBox(ABOUT_TEXT, {}) {}
                SpeechBox("${START_GAME_START}1$START_GAME_END", {}) {}
                SpeechBox(NO_COINS, {}) {}
            }
        }
    }
}