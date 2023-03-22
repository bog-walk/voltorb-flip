package dev.bogwalk.ui.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.onClick
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.bogwalk.ui.style.*
import dev.bogwalk.ui.util.drawBorder

@Composable
fun MemoPad(
    memoData: BooleanArray,
    onEditMemoRequest: (Int) -> Unit,
    onReturnRequest: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 10.dp)
            .requiredSize(width = 120.dp, height = 170.dp)
            .background(color = memoRed)
            .drawBehind {
                drawBorder(3f, 0f, darkGrey)
                drawBorder(3f, 3.8f, memoPurple)
                drawBorder(3f, 6f, memoPink)
            }
            .offset(10.dp, 10.dp)
    ) {
        Column(
            Modifier.requiredSize(100.dp, 150.dp),
            horizontalAlignment = Alignment.End
        ) {
            Row(Modifier.fillMaxWidth().padding(bottom = 2.dp)) {
                MemoButton(0, memoData[0],
                    Modifier.fillMaxWidth(0.5f).aspectRatio(1f),
                    onEditMemoRequest)
                MemoButton(1, memoData[1],
                    Modifier.aspectRatio(1f),
                    onEditMemoRequest)
            }
            Row(Modifier.fillMaxWidth().padding(bottom = 2.dp)) {
                MemoButton(2, memoData[2],
                    Modifier.fillMaxWidth(.5f).aspectRatio(1f),
                    onEditMemoRequest)
                MemoButton(3, memoData[3],
                    Modifier.aspectRatio(1f),
                    onEditMemoRequest)
            }
            IconButton(
                onClick = { onReturnRequest() }
            ) {
                Icon(
                    painter = painterResource("memo_arrow.svg"),
                    contentDescription = "Close memo",
                    modifier = Modifier.requiredSize(45.dp),
                    tint = Color.Unspecified
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MemoButton(
    value: Int,
    hasBeenAdded: Boolean,
    modifier: Modifier,
    onEditRequest: (Int) -> Unit
) {
    Box(
        modifier = modifier
            .semantics(mergeDescendants = true) {
                role = Role.Button
            }
            .background(if (hasBeenAdded) darkGreen else disabledBlue2)
            .drawBehind {
                drawBorder(3f, 0f, if (hasBeenAdded) darkGrey else disabledBlue3)
                drawBorder(3f, 3.8f, if (hasBeenAdded) lightGreen else disabledBlue1)
            }
            .onClick { onEditRequest(value) },
        contentAlignment = Alignment.Center
    ) {
        if (value == 0) {
            Icon(
                painter = painterResource("voltorb_memo.svg"),
                contentDescription = "Voltorb",
                modifier = Modifier.requiredSize(27.dp),
                tint = Color.Unspecified  // should its color not change based on added or not
            )
        } else {
            Text(value.toString(),
                color = if (hasBeenAdded) memoYellow else memoGreen,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Center)
        }
    }
}

@Preview
@Composable
private fun MemoPadPreview() {
    VoltorbFlipTheme {
        MemoPad(booleanArrayOf(true, true, false, false), {}) {}
    }
}