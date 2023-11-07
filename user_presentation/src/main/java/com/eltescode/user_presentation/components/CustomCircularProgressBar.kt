package com.eltescode.user_presentation.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun CustomCircularProgressBar(
    modifier: Modifier = Modifier,
    durationMillis: Int = 1000,
    border: Dp = 15.dp,
    loadingColor: Color = Color.Red,
    backgroundColor: Color = Color.White
) {

    val infinityTrans = rememberInfiniteTransition(label = "")

    val angle by infinityTrans.animateFloat(
        initialValue = 0F, targetValue = 359F, animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = durationMillis, easing = FastOutSlowInEasing)
        ), label = ""
    )

    val color1 by infinityTrans.animateColor(
        initialValue = Color.Transparent,
        targetValue = loadingColor,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = durationMillis, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )
    val color2 by infinityTrans.animateColor(
        initialValue = backgroundColor,
        targetValue = loadingColor,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    val horizontalBrush = Brush.sweepGradient(listOf(color1, color2))


    BoxWithConstraints(
        modifier = modifier
            .clip(CircleShape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        val boxWithConstraintsScope = this
        val size = boxWithConstraintsScope.maxHeight
        Box(
            modifier = Modifier
                .rotate(angle)
                .size(size)
                .background(horizontalBrush)
        )

        Box(
            modifier = Modifier
                .size(size - border)
                .clip(CircleShape)
                .background(backgroundColor),
        )
    }

}

@Preview(showSystemUi = true)
@Composable
private fun CustomCircularProgressBar() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CustomCircularProgressBar(modifier = Modifier.size(100.dp))
    }
}



