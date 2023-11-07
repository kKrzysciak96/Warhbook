package com.eltescode.user_presentation.components

import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eltescode.core_ui.ui.BlueColors
import com.eltescode.core_ui.ui.SilverColors
import kotlin.math.abs

@Composable
fun CustomBackground(
    modifier: Modifier = Modifier,
    backgroundColor: Brush = Brush.linearGradient(
        listOf(
            BlueColors.color4e6db1,
            BlueColors.color91b1fd
        )
    ),
    animatedColor: Brush = Brush.linearGradient(
        listOf(
            BlueColors.color91b1fd,
            BlueColors.color4e6db1,
            SilverColors.color5f6264
        )
    )
) {
    val infinityTrans = rememberInfiniteTransition(label = "")

    val initialStartOffset = remember { StartOffset((0..8000).shuffled().first()) }

    Log.d("OFFSET", "${initialStartOffset.offsetMillis}")

    val point1 by infinityTrans.animateFloat(
        initialValue = 0.3f, targetValue = 0.7f, animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 10000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse, initialStartOffset = initialStartOffset
        ), label = ""
    )

    val point2 by infinityTrans.animateFloat(
        initialValue = 0.35f, targetValue = 0.7f, animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 10000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse, initialStartOffset = initialStartOffset
        ), label = ""
    )

    val point3 by infinityTrans.animateFloat(
        initialValue = 0.05f, targetValue = 0.7f, animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 10000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse, initialStartOffset = initialStartOffset
        ), label = ""
    )

    val point4 by infinityTrans.animateFloat(
        initialValue = 0.7f, targetValue = 0.05f, animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 10000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse, initialStartOffset = initialStartOffset
        ), label = ""
    )

    val point5 by infinityTrans.animateFloat(
        initialValue = 1f, targetValue = 1.2f, animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 10000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse, initialStartOffset = initialStartOffset
        ), label = ""
    )

    BoxWithConstraints(
        modifier = modifier
            .background(backgroundColor)
    ) {
        val width = constraints.maxWidth
        val height = constraints.maxHeight


        val pathPoint1 = Offset(0f, height * point1)
        val pathPoint2 = Offset(width * 0.1f, height * point2)
        val pathPoint3 = Offset(width * 0.4f, height * point3)
        val pathPoint4 = Offset(width * 0.75f, height * point4)
        val pathPoint5 = Offset(width * 1.4f, -height.toFloat() * point5)

        val mediumColoredPath = Path().apply {
            moveTo(pathPoint1.x, pathPoint1.y)
            standardQuadFromTo(pathPoint1, pathPoint2)
            standardQuadFromTo(pathPoint2, pathPoint3)
            standardQuadFromTo(pathPoint3, pathPoint4)
            standardQuadFromTo(pathPoint4, pathPoint5)
            lineTo(width.toFloat() + 100f, height.toFloat() + 100f)
            lineTo(-100f, height.toFloat() + 100f)
            close()
        }
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawPath(
                path = mediumColoredPath, brush = animatedColor
            )
        }
    }
}

private fun Path.standardQuadFromTo(from: Offset, to: Offset) {
    quadraticBezierTo(
        from.x,
        from.y,
        abs(from.x + to.x) / 2f,
        abs(from.y + to.y) / 2f
    )
}

@Preview
@Composable
private fun CustomBackground() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CustomBackground(modifier = Modifier.size(300.dp))
    }
}