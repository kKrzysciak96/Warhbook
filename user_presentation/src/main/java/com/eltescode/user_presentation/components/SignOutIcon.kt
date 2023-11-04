package com.eltescode.user_presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SignOutIcon(
    contentDescription: String?,
    modifier: Modifier = Modifier,
    onClickIcon: () -> Unit
) {
    Icon(
        imageVector = Icons.Default.Logout,
        contentDescription = contentDescription,
        modifier = modifier
            .padding(16.dp)
            .clickable { onClickIcon() }
    )
}


@Preview(showSystemUi = true)
@Composable
fun SignOutIcon() {
    Box(contentAlignment = Alignment.Center) {
        SettingsIcon(contentDescription = null, modifier = Modifier.size(300.dp)) {}
    }
}