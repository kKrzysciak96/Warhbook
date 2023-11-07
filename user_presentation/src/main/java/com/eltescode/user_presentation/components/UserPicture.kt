package com.eltescode.user_presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.eltescode.core_ui.ui.SilverColors

@Composable
fun UserPicture(
    userPhoto: String,
    userName: String,
    userSurname: String,
    isPhotoLoading: Boolean,
    modifier: Modifier = Modifier,
    size: Dp = 150.dp,
    onClick: () -> Unit,

    ) {

    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(100.dp))
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        if (isPhotoLoading) {
            CustomCircularProgressBar(
                modifier = Modifier.size(100.dp),
                loadingColor = SilverColors.color979c9f
            )
        } else {
            AsyncImage(
                model = userPhoto,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        onClick()
                    }
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                        startY = size.value * 2f,
                        tileMode = TileMode.Clamp
                    )
                )
        )
        CustomText(
            text = "$userName $userSurname".trim(),
            fontSize = (size.value / 15f).sp,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        )
    }

}

@Preview
@Composable
private fun UserPicture() {
    UserPicture("", "Admin", "Admin", false) {}

}