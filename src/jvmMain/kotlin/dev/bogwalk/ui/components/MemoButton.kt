package dev.bogwalk.ui.components

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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import dev.bogwalk.ui.style.*
import dev.bogwalk.ui.util.drawButtonBorder

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MemoButton(
    onOpenMemoRequest: () -> Unit
) {
    Box(
        modifier = Modifier
            .semantics(mergeDescendants = true) {
                role = Role.Button
            }
            .padding(horizontal = 8.dp, vertical = 10.dp)
            .requiredSize(width = 120.dp, height = 140.dp)
            .background(color = lightGreen)
            .drawBehind {
                drawRect(
                    Brush.verticalGradient(
                        0.6f to offWhite, 0.65f to lightGrey1,
                        0.7f to lightGrey2, 0.75f to lightGrey3
                    ),
                    Offset(x = 0f, y = size.height / 4),
                    Size(size.width, size.height / 4 * 2.5f)
                )
                drawButtonBorder()
                drawButtonBorder(darkGreen)
            }
            .onClick { onOpenMemoRequest() }
    ) {
        Icon(
            painter = painterResource("memo_x.svg"),
            contentDescription = "Memo X",
            modifier = Modifier.requiredSize(29.dp)
                .align(Alignment.TopCenter).offset(y = 20.dp),
            tint = Color.Unspecified
        )
        Text(
            "Open Memo",
            modifier = Modifier.fillMaxWidth(0.5f)
                .align(Alignment.Center).offset(y = 20.dp),
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Preview
@Composable
private fun MemoButtonPreview() {
    VoltorbFlipTheme {
        MemoButton {}
    }
}