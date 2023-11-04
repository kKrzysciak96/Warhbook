package com.eltescode.core_ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.imageResource
import com.eltescode.core_ui.R

@Composable
fun backgroundShaderBrush(): ShaderBrush {
    return ShaderBrush(
        ImageShader(
            image = ImageBitmap.imageResource(id = R.drawable.splash_background),
            tileModeX = TileMode.Repeated,
            tileModeY = TileMode.Repeated
        )
    )
}

@Composable
fun backgroundBrush(): Brush {
    return Brush.linearGradient(
        colors = listOf(Color.White, Color(0XFF979c9f)),
        start = Offset.Zero,
        end = Offset.Infinite
    )
}