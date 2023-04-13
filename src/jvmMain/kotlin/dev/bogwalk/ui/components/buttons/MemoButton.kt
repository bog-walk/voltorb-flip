package dev.bogwalk.ui.components.buttons

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.*
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.unit.dp
import dev.bogwalk.ui.Screen
import dev.bogwalk.ui.style.*
import dev.bogwalk.ui.util.drawLineBorder

@OptIn(ExperimentalTextApi::class)
@Composable
fun MemoButton(
    screen: Screen,
    isMemoOpen: Boolean,
    onSelectRequest: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .semantics(mergeDescendants = true) {
                if (screen != Screen.IN_GAME) disabled()
            }
            .testTag(MEMO_TAG)
            .padding(vertical = 12.dp, horizontal = 10.dp)
            .requiredSize(90.dp, 100.dp)
            .background(lightGreen)
            .drawBehind {
                drawLineBorder(outerColor = darkGrey)
                drawLineBorder(innerColor = darkGreen)
            }
            .offset(y = 10.dp)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,  // this prevents mouse hover effect
                enabled = screen == Screen.IN_GAME,
                role = Role.Button
            ) { onSelectRequest() },
        contentAlignment = Alignment.TopCenter
    ) {
        Box(
            Modifier
                .padding(top = 15.dp)
                .width(82.dp)
                .fillMaxHeight(.75f)
                .background(Brush.verticalGradient(
                    .57f to offWhite, .6f to lightGrey1,
                    .63f to lightGrey2, .66f to lightGrey3
                ), RoundedCornerShape(2.dp))
                .padding(top = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (isMemoOpen) CLOSE else OPEN,
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = if (isMemoOpen) CLOSE else OPEN,
                style = MaterialTheme.typography.labelLarge.copy(
                    color = offWhite,
                    drawStyle = null
                )
            )
        }
        Icon(
            painter = painterResource(MEMO_X),
            contentDescription = MEMO_X_DESCR,
            modifier = Modifier.requiredSize(23.dp),
            tint = Color.Unspecified
        )
    }
}

@Preview
@Composable
private fun MemoButtonPreview() {
    VoltorbFlipTheme {
        Column {
            MemoButton(Screen.IN_GAME, false)
            MemoButton(Screen.IN_GAME, true)
        }
    }
}