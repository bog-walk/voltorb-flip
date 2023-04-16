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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.*
import androidx.compose.ui.unit.dp
import dev.bogwalk.ui.Screen
import dev.bogwalk.ui.style.*
import dev.bogwalk.ui.util.drawBorder

@Composable
fun MemoPad(
    screen: Screen,
    memoData: List<Boolean>?,
    onEditRequest: (Int) -> Unit = {},
    onCloseRequest: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .requiredWidth(memoButtonWidth)
            .background(memoRed)
            .drawBehind {
                drawBorder(thinBorder.toPx(), 0f, darkGrey, StrokeCap.Butt)
                drawBorder(thinBorder.toPx(), 2.2.dp.toPx(), memoPurple)
                drawBorder(thinBorder.toPx(), 3.8.dp.toPx(), memoPink)
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(standardPadding),
            horizontalAlignment = Alignment.End
        ) {
            Row(Modifier.fillMaxWidth()) {
                MemoPadButton(Modifier.weight(.5f),
                    screen, 0, memoData?.get(0),
                    onEditRequest = onEditRequest) {}
                MemoPadButton(Modifier.weight(.5f),
                    screen, 1, memoData?.get(1),
                    onEditRequest = onEditRequest) {}
            }
            Row(Modifier.fillMaxWidth()) {
                MemoPadButton(Modifier.weight(.5f),
                    screen, 2, memoData?.get(2),
                    onEditRequest = onEditRequest) {}
                MemoPadButton(Modifier.weight(.5f),
                    screen, 3, memoData?.get(3),
                    onEditRequest = onEditRequest) {}
            }
            // return arrow should always be enabled unless used in info screen
            MemoPadButton(Modifier.fillMaxWidth(.5f),
                screen, -1, null, {},
                onCloseRequest = onCloseRequest)
        }
    }
}

@Composable
private fun MemoPadButton(
    modifier: Modifier,
    screen: Screen,
    value: Int,
    hasBeenAdded: Boolean?,
    onEditRequest: (Int) -> Unit,
    onCloseRequest: () -> Unit
) {
    Box(
        modifier = modifier
            .semantics {
                if (screen != Screen.IN_GAME ||
                    value > -1 && hasBeenAdded == null) disabled()
            }
            .testTag(MEMO_PAD_TAG)
            .aspectRatio(1f)
            .padding(thinBorder)
            .background(when (hasBeenAdded) {
                true -> darkGreen
                false -> disabledBlue2
                else -> Color.Transparent
            })
            .border(thinBorder, when (hasBeenAdded) {
                true -> lightGreen
                false -> disabledBlue1
                else -> memoPink
            })
            .drawBehind {
                if (value != -1 && hasBeenAdded != null) {
                    drawBorder(thinBorder.toPx(), (-1).dp.toPx(),
                        if (hasBeenAdded) darkGrey else disabledBlue3, StrokeCap.Round)
                }
            }
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,  // this prevents mouse hover effect
                enabled = screen == Screen.IN_GAME &&
                        (value == -1 || value > -1 && hasBeenAdded != null),
                role = Role.Button
            ) {
                if (value == -1) onCloseRequest() else onEditRequest(value)
              },
        contentAlignment = Alignment.Center
    ) {
        when (value) {
            -1 -> Icon(
                painter = painterResource(MEMO_ARROW),
                contentDescription = MEMO_ARROW_DESCR,
                modifier = Modifier.requiredSize(memoArrowSize),
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
                modifier = Modifier.requiredSize(memoZeroSize * 2),
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
            MemoPad(Screen.IN_GAME, memoData = null, onEditRequest = {}) {}
            // enabled with nothing cached
            MemoPad(Screen.IN_GAME, memoData = List(4) { false },
                onEditRequest = {}) {}
            // enabled with half cached
            MemoPad(Screen.IN_GAME, memoData = listOf(true, true, false, false),
                onEditRequest = {}) {}
            // enabled with all cached
            MemoPad(Screen.IN_GAME, memoData = List(4) { true },
                onEditRequest = {}) {}
        }
    }
}