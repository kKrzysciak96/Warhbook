package com.eltescode.photo_presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.eltescode.core_ui.R

@Composable
fun PhotoDialog(
    photoUrl: String,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onButtonClick: (String) -> Unit
) {
    val configuration = LocalConfiguration.current.orientation
    Dialog(onDismissRequest = onDismiss) {
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            Card(
                modifier.padding(8.dp)
            ) {

                Column(
                    modifier = modifier.padding(8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(36.dp)
                            .padding(4.dp)
                            .align(Alignment.End)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                        )
                    }
                    AsyncImage(
                        model = photoUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .size(
                                if (configuration == Configuration.ORIENTATION_PORTRAIT) {
                                    250.dp
                                } else {
                                    200.dp
                                }
                            )
                            .padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedButton(
                        onClick = {
                            onButtonClick(photoUrl)
                            onDismiss()
                        },
                        modifier = Modifier.clip(RoundedCornerShape(100.dp))
                    ) {
                        Text(text = stringResource(id = R.string.set_profile_picture))
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PhotoDialog() {

    PhotoDialog(photoUrl = "",
        modifier = Modifier
            .width(300.dp)
            .background(Color.White),
        onDismiss = {}) {}

}