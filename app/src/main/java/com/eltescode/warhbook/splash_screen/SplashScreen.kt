package com.eltescode.warhbook.splash_screen

import android.animation.TimeInterpolator
import android.annotation.SuppressLint
import android.view.animation.BounceInterpolator
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FloatTweenSpec
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.eltescode.core_ui.R
import com.eltescode.core_ui.components.backgroundShaderBrush
import kotlinx.coroutines.delay
import kotlin.math.roundToInt


@Composable
fun SplashScreen(onNextScreen: () -> Unit) {

    val isVisible = remember { mutableStateOf(false) }

    val pxToMove = with(LocalDensity.current) { -300.dp.toPx().roundToInt() }
    val pxStart = with(LocalDensity.current) { 50.dp.toPx().roundToInt() }
    var crackState by remember { mutableIntStateOf(0) }
    val valueAngle = remember { Animatable(initialValue = 360f) }
    val valueOffset = remember { Animatable(initialValue = pxStart.toFloat()) }

    LaunchedEffect(key1 = true) {
        isVisible.value = true
        delay(1000)
        valueAngle.animateTo(
            targetValue = -95f,
            animationSpec = FloatTweenSpec(2500, 0, LinearOutSlowInEasing)
        )
        valueAngle.animateTo(targetValue = -135f, animationSpec = tween(1500))
    }

    LaunchedEffect(key1 = true) {
        delay(1000)
        valueOffset.animateTo(
            targetValue = pxToMove.toFloat(),
            animationSpec = FloatTweenSpec(2500, 0, LinearOutSlowInEasing)
        )
        valueOffset.animateTo(
            targetValue = pxStart.toFloat(),
            animationSpec = FloatTweenSpec(2000, 0, BounceInterpolator().toEasing())
        )

    }

    LaunchedEffect(key1 = true) {
        delay(4095)
        crackState = 1
        delay(600)
        crackState = 2
        delay(400)
        crackState = 3
        delay(200)
        crackState = 4
        delay(200)
        isVisible.value = false
        delay(800)
        onNextScreen()
    }

    SplashScreen(
        isVisible = isVisible,
        crackState = crackState,
        valueOffset = valueOffset,
        valueAngle = valueAngle,
    )
}


@Composable
private fun SplashScreen(
    isVisible: MutableState<Boolean>,
    crackState: Int,
    valueOffset: Animatable<Float, AnimationVector1D>,
    valueAngle: Animatable<Float, AnimationVector1D>,
) {


    AnimatedVisibility(
        visible = isVisible.value,
        enter = fadeIn(animationSpec = tween(durationMillis = 1000)),
        exit = fadeOut(animationSpec = tween(durationMillis = 1000))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundShaderBrush()),
            contentAlignment = Alignment.Center
        ) {

            when (crackState) {
                1 -> {
                    CrackStateRelevantImage(id = R.drawable.broken_screen_1)
                }

                2 -> {
                    CrackStateRelevantImage(id = R.drawable.broken_screen_2)
                }

                3 -> {
                    CrackStateRelevantImage(id = R.drawable.broken_screen_3)
                }

                4 -> {
                    CrackStateRelevantImage(id = R.drawable.broken_screen_4)
                }
            }
            NamePlate(
                modifier = Modifier
                    .size(550.dp)
                    .align(Alignment.TopCenter)
                    .padding(16.dp)
            )
            if (crackState != 4) {
                HammerImage(
                    valueOffset = valueOffset,
                    valueAngle = valueAngle,
                    modifier = Modifier
                        .size(75.dp)
                        .align(Alignment.BottomCenter)
                )
            }
        }
    }
}

private fun TimeInterpolator.toEasing() = Easing { x ->
    getInterpolation(x)
}

@Composable
private fun CrackStateRelevantImage(id: Int) {
    Image(
        painter = painterResource(id = id),
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize()
    )
}

@Composable
private fun NamePlate(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.name_plate),
        contentDescription = null,
        modifier = modifier
    )
}

@Composable
fun HammerImage(
    valueOffset: Animatable<Float, AnimationVector1D>,
    valueAngle: Animatable<Float, AnimationVector1D>,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = R.drawable.hammer),
        contentDescription = null,
        modifier = modifier
            .offset { IntOffset(0, valueOffset.value.roundToInt()) }
            .rotate(valueAngle.value)
    )
}

@SuppressLint("UnrememberedMutableState", "UnrememberedAnimatable")
@Preview
@Composable
private fun SplashScreen() {
    SplashScreen(
        isVisible = mutableStateOf(true),
        crackState = 4,
        valueOffset = Animatable(0f),
        valueAngle = Animatable(0f),
    )
}
