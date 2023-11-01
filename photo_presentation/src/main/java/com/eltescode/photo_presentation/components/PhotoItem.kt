package com.eltescode.photo_presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage

@Composable
fun PhotoItem(url: String?, modifier: Modifier = Modifier) {
    AsyncImage(model = url, contentDescription = null, modifier = modifier)
}