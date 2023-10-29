package com.eltescode.auth_presentation.sign_up_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.eltescode.auth_presentation.components.PasswordTextField
import com.eltescode.auth_presentation.utils.SignUpScreenEvent
import com.eltescode.auth_presentation.utils.SignUpScreenState
import com.eltescode.core_ui.R
import com.eltescode.core_ui.ui.fontFamily_croissant
import com.eltescode.core_ui.utils.UiEvent


@Composable
fun SignUpScreen(
    snackBarHostState: SnackbarHostState,
    viewModel: SignUpViewModel = hiltViewModel(),
    onSuccess: () -> Unit
) {
    val state = viewModel.state

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->

            when (event) {
                is UiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(event.message.asString(context))
                }

                UiEvent.Success -> {
                    onSuccess()
                }

                else -> Unit
            }
        }
    }
    SignUpScreen(
        state = state,
        onEvent = viewModel::onEvent
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(state: SignUpScreenState, onEvent: (SignUpScreenEvent) -> Unit) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .padding(2.dp),
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.register_header),
                    fontFamily = fontFamily_croissant,
                    fontSize = 32.sp,
                    modifier = Modifier
                )
                TextField(
                    value = state.email,
                    onValueChange = { onEvent(SignUpScreenEvent.OnEmailEntered(it)) },
                    modifier = Modifier.padding(bottom = 12.dp),
                    label = { Text(text = stringResource(id = R.string.enter_email_label)) })
                PasswordTextField(
                    password = state.password,
                    isPasswordVisible = state.isPasswordVisible,
                    onPasswordEntered = { onEvent(SignUpScreenEvent.OnPasswordEntered(it)) },
                    onShowPasswordClick = { onEvent(SignUpScreenEvent.OnShowPasswordClick) },
                    labelText = stringResource(id = R.string.enter_password_label),
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                PasswordTextField(
                    password = state.repeatedPassword,
                    isPasswordVisible = state.isPasswordVisible,
                    onPasswordEntered = { onEvent(SignUpScreenEvent.OnRepeatedPasswordEntered(it)) },
                    onShowPasswordClick = { onEvent(SignUpScreenEvent.OnShowPasswordClick) },
                    labelText = stringResource(id = R.string.repeat_password_label),
                    isError = state.password != state.repeatedPassword,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                OutlinedButton(
                    onClick = { onEvent(SignUpScreenEvent.OnSignUpClick) },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Black,
                        disabledContentColor = Color.Gray,
                        containerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent
                    ),
                    border = BorderStroke(2.dp, Color.Black),
                    modifier = Modifier.width(150.dp),
                    shape = CircleShape
                ) {
                    Text(text = "Sign Up", fontFamily = fontFamily_croissant, fontSize = 20.sp)
                }
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(SignUpScreenState()) {}
}