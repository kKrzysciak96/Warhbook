package com.eltescode.core_ui.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun BoxWithAnimatedBorder(
    modifier: Modifier = Modifier,
    borderWidth: Dp = 4.dp,
    animatingBorderLength: Dp = 50.dp,
    borderColor: Brush = Brush.linearGradient(listOf(Color.LightGray, Color.DarkGray, Color.Black)),
    brush: Brush = Brush.horizontalGradient(listOf(Color.Transparent, Color.White)),
    contentBackground: Brush = Brush.linearGradient(
        colors = listOf(Color.LightGray, Color.White),
        start = Offset.Infinite,
        end = Offset.Zero
    ),
    clipShape: Shape = RoundedCornerShape(topEnd = 100.dp, bottomStart = 100.dp),
    duration: Int = 10000,
    content: @Composable () -> Unit = {}
) {
    val infinityTrans = rememberInfiniteTransition(label = "")

    val angle by infinityTrans.animateFloat(
        initialValue = 0F, targetValue = 359F, animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = duration
                0f at 0
                359f at 10000
            }
        ), label = ""
    )
    BoxWithConstraints(
        modifier = modifier
            .clip(clipShape)
            .background(borderColor)
    ) {
        val boxWithConstraintsScope = this
        val maxHeight = boxWithConstraintsScope.maxHeight / 2

        Box(
            modifier = Modifier
                .fillMaxSize()
        )
        Box(
            modifier = Modifier
                .rotate(angle)
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .width(animatingBorderLength)
                    .height(maxHeight)
                    .offset(x = -(maxHeight / 2), y = -(maxHeight / 2))
                    .rotate(-45f)
                    .background(brush)
                    .align(Alignment.TopCenter)
            )
            Box(
                modifier = Modifier
                    .width(animatingBorderLength)
                    .height(maxHeight)
                    .offset(x = maxHeight / 2, y = maxHeight / 2)
                    .rotate(135f)
                    .background(brush)
                    .align(Alignment.BottomCenter)
            )
        }
        Box(
            modifier = Modifier
                .scale(
                    scaleY = (boxWithConstraintsScope.maxHeight.value - borderWidth.value) / boxWithConstraintsScope.maxHeight.value,
                    scaleX = (boxWithConstraintsScope.maxWidth.value - borderWidth.value) / boxWithConstraintsScope.maxWidth.value
                )
                .height(boxWithConstraintsScope.maxHeight)
                .width(boxWithConstraintsScope.maxWidth)
                .clip(clipShape)
                .background(contentBackground)
        ) {
            content()
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun BoxWithFancyBorder() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        BoxWithAnimatedBorder(
            borderWidth = 5.dp,
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            clipShape = RoundedCornerShape(
                topEnd = 50.dp, bottomStart = 50.dp
            )
        )
    }
}