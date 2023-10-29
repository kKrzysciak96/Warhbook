package com.eltescode.auth_presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eltescode.core_ui.ui.fontFamily_croissant

@Composable
fun AuthButton(
    buttonText: String,
    enabled: Boolean,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 20.sp,
    borderWidth: Dp = 2.dp,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.Black,
            disabledContentColor = Color.Gray,
            containerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent
        ),
        border = if (enabled) BorderStroke(borderWidth, Color.Black)
        else BorderStroke(borderWidth, Color.Gray),
        modifier = modifier,
        shape = CircleShape,
        enabled = enabled
    ) {
        Text(text = buttonText, fontFamily = fontFamily_croissant, fontSize = fontSize)
    }
}