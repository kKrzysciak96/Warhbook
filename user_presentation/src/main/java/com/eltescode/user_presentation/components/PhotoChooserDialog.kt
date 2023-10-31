package com.eltescode.user_presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.PhotoAlbum
import androidx.compose.material.icons.filled.WebAsset
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.eltescode.core_ui.R
import com.eltescode.user_presentation.utils.UserDataScreenEvent

@Composable
fun PhotoChooserDialog(
    onDialogDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    onEvent: (UserDataScreenEvent) -> Unit
) {
    Dialog(onDismissRequest = { onDialogDismiss() }) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ChooserRow(
                    icon = Icons.Default.CameraAlt,
                    text = stringResource(id = R.string.take_picture),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp, start = 16.dp, end = 16.dp),
                ) {
                    onDialogDismiss()
                    onEvent(UserDataScreenEvent.PhotoDialogEvents.OnTakePhotoClick)
                }
                ChooserRow(
                    icon = Icons.Default.PhotoAlbum,
                    text = stringResource(id = R.string.choose_from_album),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp, start = 16.dp, end = 16.dp),
                ) {
                    onDialogDismiss()
                    onEvent(UserDataScreenEvent.PhotoDialogEvents.OnChooseFromAlbumClick)
                }
                ChooserRow(
                    icon = Icons.Default.WebAsset,
                    text = stringResource(id = R.string.choose_from_internet),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp, start = 16.dp, end = 16.dp),
                ) {
                    onDialogDismiss()
                    onEvent(UserDataScreenEvent.PhotoDialogEvents.OnChooseFromInternetClick)
                }
            }
        }
    }
}

@Composable
private fun ChooserRow(
    icon: ImageVector,
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier
                .padding(8.dp)
        )
        CustomText(
            text = text,
        )
    }
}


@Preview(showSystemUi = true)
@Composable
fun PhotoChooserDialog() {
    Box(contentAlignment = Alignment.Center) {
        PhotoChooserDialog(modifier = Modifier
            .size(300.dp)
            .background(Color.White), onDialogDismiss = {}, onEvent = {})
    }
}




