package com.eltescode.core_ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.eltescode.core_ui.ui.SilverColors.Companion.color4c4e50


@OptIn(ExperimentalMaterial3Api::class)


@Composable
fun texFieldColors_1(): TextFieldColors {

    return TextFieldDefaults.textFieldColors(
        containerColor = Color.Transparent,
        focusedIndicatorColor = color4c4e50,
        unfocusedSupportingTextColor = Color.Black,
        textColor = Color.Black,
        focusedLabelColor = Color.Black,
        focusedLeadingIconColor = Color.Black,
        cursorColor = Color.Black

    )
}