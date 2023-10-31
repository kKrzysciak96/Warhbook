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
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.eltescode.core_ui.R
import com.eltescode.user_presentation.utils.UserDataScreenEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsDialog(
    userEmail: String,
    userName: String,
    userSurname: String,
    onDialogDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    onEvent: (UserDataScreenEvent) -> Unit
) {
    Dialog(onDismissRequest = { onDialogDismiss() }) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            ConfirmIcon(
                modifier = Modifier.align(Alignment.TopEnd),
                onClick = {
                    onDialogDismiss()
                    onEvent(UserDataScreenEvent.OnSettingsSave)
                })
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomText(text = userEmail)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp, start = 16.dp, end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomText(
                        text = stringResource(id = R.string.name_label),
                        modifier = Modifier.width(100.dp)
                    )
                    TextField(
                        value = userName,
                        onValueChange = { onEvent(UserDataScreenEvent.OnNameEntered(it)) },
                        placeholder = { Text(text = stringResource(id = R.string.enter_name)) }
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(top = 32.dp, start = 16.dp, end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomText(
                        text = stringResource(id = R.string.surname_label),
                        modifier = Modifier.width(100.dp)
                    )
                    TextField(
                        value = userSurname,
                        onValueChange = { onEvent(UserDataScreenEvent.OnSurnameEntered(it)) },
                        placeholder = { Text(text = stringResource(id = R.string.enter_surname)) }
                    )
                }
            }
        }
    }
}

@Composable
fun ConfirmIcon(modifier: Modifier, onClick: () -> Unit) {
    Icon(
        imageVector = Icons.Default.Save,
        contentDescription = null,
        modifier = modifier
            .padding(8.dp)
            .clickable { onClick() })
}


@Preview(showSystemUi = true)
@Composable
fun SettingsDialog() {
    Box(contentAlignment = Alignment.Center) {
        SettingsDialog(
            userEmail = "developer@adnroid.com",
            userName = "Mark",
            userSurname = "Suckeberg",
            modifier = Modifier
                .size(300.dp)
                .background(Color.White),
            onDialogDismiss = {},
            onEvent = {})
    }
}