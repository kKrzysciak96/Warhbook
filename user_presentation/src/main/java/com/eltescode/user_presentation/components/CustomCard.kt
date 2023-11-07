package com.eltescode.user_presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomCard(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 32.sp,
) {
    Box(
        modifier = modifier
            .clip(
                RoundedCornerShape(
                    topStart = 25.dp,
                    topEnd = 40.dp,
                    bottomEnd = 25.dp
                )
            ),
        contentAlignment = Alignment.Center
    )

    {
        CustomBackground(
            modifier = Modifier.fillMaxSize(),
        )
        CustomText(
            text = text,
            fontSize = fontSize,
            color = Color.White,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun CustomCard() {
    Box(contentAlignment = Alignment.Center) {
        CustomCard(text = "Notes", modifier = Modifier.size(300.dp))
    }
}