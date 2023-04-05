package dev.bogwalk.ui.components.buttons

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.disabled
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
    memoData: BooleanArray?,
    inGameUse: Boolean = true,
    onEditRequest: (Int) -> Unit = {},
    onQuitRequest: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .requiredWidth(90.dp)
            .background(memoRed)
            .drawBehind {
                drawBorder(2.dp.toPx(), 0f, darkGrey, StrokeCap.Butt)
                drawBorder(2.dp.toPx(), 2.2.dp.toPx(), memoPurple)
                drawBorder(2.dp.toPx(), 3.8.dp.toPx(), memoPink)
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            horizontalAlignment = Alignment.End
        ) {
            Row(Modifier.fillMaxWidth()) {
                MemoPadButton(Modifier.weight(.5f),
                    0, memoData?.get(0), inGameUse,
                    onEditRequest = onEditRequest) {}
                MemoPadButton(Modifier.weight(.5f),
                    1, memoData?.get(1), inGameUse,
                    onEditRequest = onEditRequest) {}
            }
            Row(Modifier.fillMaxWidth()) {
                MemoPadButton(Modifier.weight(.5f),
                    2, memoData?.get(2), inGameUse,
                    onEditRequest = onEditRequest) {}
                MemoPadButton(Modifier.weight(.5f),
                    3, memoData?.get(3), inGameUse,
                    onEditRequest = onEditRequest) {}
            }
            MemoPadButton(Modifier.fillMaxWidth(.5f),
                -1, null, inGameUse, {},
                onQuitRequest = onQuitRequest)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MemoPadButton(
    modifier: Modifier,
    value: Int,
    hasBeenAdded: Boolean?,
    inGameUse: Boolean,
    onEditRequest: (Int) -> Unit,
    onQuitRequest: () -> Unit
) {
    Box(
        modifier = modifier
            .semantics(mergeDescendants = true) {
                role = Role.Button
                if (!inGameUse || value > -1 && hasBeenAdded == null) disabled()
            }
            .aspectRatio(1f)
            .padding(2.dp)
            .background(when (hasBeenAdded) {
                true -> darkGreen
                false -> disabledBlue2
                else -> Color.Transparent
            })
            .border(2.dp, when (hasBeenAdded) {
                true -> lightGreen
                false -> disabledBlue1
                else -> memoPink
            })
            .drawBehind {
                if (value != -1 && hasBeenAdded != null) {
                    drawBorder(2.dp.toPx(), (-1).dp.toPx(),
                        if (hasBeenAdded) darkGrey else disabledBlue3, StrokeCap.Round)
                }
            }
            .onClick(enabled = inGameUse) {
                if (value == -1) onQuitRequest() else onEditRequest(value)
            },
        contentAlignment = Alignment.Center
    ) {
        when (value) {
            -1 -> Icon(
                painter = painterResource("memo_arrow.svg"),
                contentDescription = null,
                modifier = Modifier.requiredSize(27.dp),
                tint = Color.Unspecified
            )
            0 -> Icon(
                painter = painterResource("memo_zero.svg"),
                contentDescription = null,
                modifier = Modifier.requiredSize(18.dp),
                tint = Color.Unspecified
            )
            else -> Text(
                text = value.toString(),
                color = when (hasBeenAdded) {
                    true -> memoYellow
                    false -> memoGreen
                    else -> memoPink
                },
                fontSize = 15.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.Monospace,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
private fun MemoPadPreview() {
    VoltorbFlipTheme {
        Column {
            // fully disabled
            MemoPad(memoData = null, onEditRequest = {}) {}
            // enabled with nothing cached
            MemoPad(memoData = booleanArrayOf(false, false, false, false), onEditRequest = {}) {}
            // enabled with half cached
            MemoPad(memoData = booleanArrayOf(true, true, false, false), onEditRequest = {}) {}
            // enabled with all cached
            MemoPad(memoData = booleanArrayOf(true, true, true, true), onEditRequest = {}) {}
        }
    }
}