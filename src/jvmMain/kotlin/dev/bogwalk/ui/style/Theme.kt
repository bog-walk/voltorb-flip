package dev.bogwalk.ui.style

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

val lightGreen = Color(0xff28a068)
val darkGreen = Color(0xff188060)

val lightRed = Color(0xffb88880)
val mediumRed = Color(0xffa05850)
val darkRed = Color(0xff683028)
val brightRed = Color(0xfff84030)

val darkGrey = Color(0xff404040)
val slateGrey1 = Color(0xff383838)
val slateGrey2 = Color(0xff282828)

val mediumGrey = Color(0xff585850)
val blueGrey = Color(0xffa8b8b8)

val offWhite = Color(0xfff8f8f8)
val lightGrey1 = Color(0xffe0e0e0)
val lightGrey2 = Color(0xffc8c8c8)
val lightGrey3 = Color(0xffa8a8a8)

val greenWhite = Color(0xffd0e8e0)
val greenWhite2 = Color(0xffa0c0b0)

val lightBlue1 = Color(0xff68a8f8)
val lightBlue2 = Color(0xff4888f0)
val lightBlue3 = Color(0xff3068e0)

val memoBrightYellow = Color(0xfff8c830)
val memoYellow = Color(0xfff8b830)
val memoBrown = Color(0xff785820)
val memoPink = Color(0xffe89880)
val memoPurple = Color(0xff906860)
val memoRed = Color(0xffc07050)
val memoGreen = Color(0xff609060)
val disabledBlue1 = Color(0xff98b8b8)
val disabledBlue2 = Color(0xff88a8a8)
val disabledBlue3 = Color(0xff707070)

val rowColors = listOf(
    Color(0xffe07050), // red
    Color(0xff40a840), // green
    Color(0xffe8a038), // yellow
    Color(0xff3090f8), // blue
    Color(0xffc060e0)  // purple
)

private val VoltorbFlipColorScheme = lightColorScheme(
    background = darkGreen,
    surface = lightGreen
)

@OptIn(ExperimentalTextApi::class)
private val VoltorbFlipTypography = Typography(
    titleLarge = TextStyle(  // coin counter
        color = darkGrey,
        fontSize = 40.sp,
        fontWeight = FontWeight.ExtraBold,
        fontFamily = FontFamily.SansSerif,
        letterSpacing = 2.sp,
        shadow = Shadow(blueGrey, Offset(0f, 3f),1f),
        textAlign = TextAlign.Center
    ),
    titleMedium = TextStyle(  // info text (on green)
        color = offWhite,
        fontSize = 20.sp,
        fontFamily = FontFamily.Monospace,
        shadow = Shadow(mediumGrey, Offset(1f, 2f),0f),
        textAlign = TextAlign.Center
    ),
    bodyMedium = TextStyle(  // info box text (on white)
        color = mediumGrey,
        fontSize = 18.sp,
        fontWeight = FontWeight.Light,
        fontFamily = FontFamily.Monospace,
        shadow = Shadow(blueGrey, Offset(1f, 1f),0f),
        lineHeight = 1.2.em,
        textAlign = TextAlign.Start
    ),
    bodySmall = TextStyle(  // unflipped tile with memo
        color = memoBrightYellow,
        fontSize = 12.sp,
        fontWeight = FontWeight.ExtraBold,
        fontFamily = FontFamily.SansSerif,
        textAlign = TextAlign.Center
    ),
    labelLarge = TextStyle(  // buttons with stroke
        color = darkGrey,
        fontSize = 20.sp,
        fontFamily = FontFamily.Monospace,
        drawStyle = Stroke(width = 4f, join = StrokeJoin.Round),
        textAlign = TextAlign.Center
    ),
    labelMedium = TextStyle(  // memo pad numbers
        fontSize = 17.sp,
        fontWeight = FontWeight.ExtraBold,
        fontFamily = FontFamily.SansSerif,
        textAlign = TextAlign.Center
    ),
    labelSmall = TextStyle(  // info tile
        color = darkGrey,
        fontSize = 15.sp,
        fontWeight = FontWeight.ExtraBold,
        fontFamily = FontFamily.SansSerif,
        letterSpacing = 1.sp,
        textAlign = TextAlign.Right
    )
)

@Composable
fun VoltorbFlipTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = VoltorbFlipColorScheme,
        typography = VoltorbFlipTypography
    ) {
        Surface(
            color = darkGrey,
            content = content
        )
    }
}