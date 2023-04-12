package dev.bogwalk.ui.components.buttons

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.*
import androidx.compose.ui.unit.dp
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
                    inGameUse, 0, memoData?.get(0),
                    onEditRequest = onEditRequest) {}
                MemoPadButton(Modifier.weight(.5f),
                    inGameUse, 1, memoData?.get(1),
                    onEditRequest = onEditRequest) {}
            }
            Row(Modifier.fillMaxWidth()) {
                MemoPadButton(Modifier.weight(.5f),
                    inGameUse, 2, memoData?.get(2),
                    onEditRequest = onEditRequest) {}
                MemoPadButton(Modifier.weight(.5f),
                    inGameUse, 3, memoData?.get(3),
                    onEditRequest = onEditRequest) {}
            }
            // return arrow should always be enabled unless used in info screen
            MemoPadButton(Modifier.fillMaxWidth(.5f),
                inGameUse, -1, null, {},
                onQuitRequest = onQuitRequest)
        }
    }
}

@Composable
private fun MemoPadButton(
    modifier: Modifier,
    inGameUse: Boolean,
    value: Int,
    hasBeenAdded: Boolean?,
    onEditRequest: (Int) -> Unit,
    onQuitRequest: () -> Unit
) {
    Box(
        modifier = modifier
            .semantics(mergeDescendants = true) {
                testTag = MEMO_PAD_TAG
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
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,  // this prevents mouse hover effect
                enabled = inGameUse && (value == -1 || value > -1 && hasBeenAdded != null),
                role = Role.Button
            ) {
                if (value == -1) onQuitRequest() else onEditRequest(value)
              },
        contentAlignment = Alignment.Center
    ) {
        when (value) {
            -1 -> Icon(
                painter = painterResource(MEMO_ARROW),
                contentDescription = MEMO_ARROW_DESCR,
                modifier = Modifier.requiredSize(32.dp),
                tint = Color.Unspecified
            )
            0 -> Icon(
                painter = painterResource(when (hasBeenAdded) {
                    true -> MEMO_ZERO_ACTIVE
                    false -> MEMO_ZERO_INACTIVE
                    else -> MEMO_ZERO_DISABLED
                }),
                contentDescription = when (hasBeenAdded) {
                    true -> MEMO_ZERO_ACTIVE_DESCR
                    false -> MEMO_ZERO_INACTIVE_DESCR
                    else -> MEMO_ZERO_DISABLED_DESCR
                },
                modifier = Modifier.requiredSize(20.dp),
                tint = Color.Unspecified
            )
            else -> Text(
                text = value.toString(),
                color = when (hasBeenAdded) {
                    true -> memoYellow
                    false -> memoGreen
                    else -> memoPink
                },
                style = MaterialTheme.typography.labelMedium
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