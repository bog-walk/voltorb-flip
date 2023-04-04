package dev.bogwalk.ui.components.buttons

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.onClick
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import dev.bogwalk.ui.style.*
import dev.bogwalk.ui.util.drawLineBorder

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MemoButton(
    isMemoOpen: Boolean,
    inGameUse: Boolean = true,
    onSelectRequest: () -> Unit = {}
) {
    Box(
        Modifier
            .semantics(mergeDescendants = true) {
                role = Role.Button
                if (!inGameUse) disabled()  // in info screen
            }
            .padding(horizontal = 3.dp, vertical = 12.dp)
            .requiredSize(90.dp, 100.dp)
            .background(lightGreen)
            .drawBehind {
                drawRoundRect(
                    Brush.verticalGradient(
                        .57f to offWhite, .6f to lightGrey1,
                        .63f to lightGrey2, .66f to lightGrey3
                    ),
                    Offset(x = 4.dp.toPx(), y = size.height / 4),
                    Size(size.width - 8.dp.toPx(), size.height / 4 * 3 - 10.dp.toPx()),
                    CornerRadius(2.dp.toPx())
                )
                drawLineBorder(outerColor = darkGrey)
                drawLineBorder(innerColor = darkGreen)
            }
            .onClick(enabled = inGameUse) { onSelectRequest() }
    ) {
        Icon(
            painter = painterResource("memo_x.svg"),
            contentDescription = null,
            modifier = Modifier
                .requiredSize(23.dp)
                .align(Alignment.TopCenter)
                .offset(y = 10.dp),
            tint = Color.Unspecified
        )
        Text(
            text = "${if (isMemoOpen) "Close" else "Open"} Memo",
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = 10.dp),
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Preview
@Composable
private fun MemoButtonPreview() {
    VoltorbFlipTheme {
        Column {
            MemoButton(isMemoOpen = false)
            MemoButton(isMemoOpen = true)
        }
    }
}