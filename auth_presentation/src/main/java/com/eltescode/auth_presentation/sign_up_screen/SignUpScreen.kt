package com.eltescode.auth_presentation.sign_up_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.eltescode.auth_presentation.components.AuthButton
import com.eltescode.auth_presentation.components.PasswordTextField
import com.eltescode.auth_presentation.utils.SignUpScreenEvent
import com.eltescode.auth_presentation.utils.SignUpScreenState
import com.eltescode.core_ui.R
import com.eltescode.core_ui.components.BoxWithAnimatedBorder
import com.eltescode.core_ui.components.silverBackgroundBrush
import com.eltescode.core_ui.components.texFieldColors_1
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

    LaunchedEffect(true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {

                    snackBarHostState.currentSnackbarData?.dismiss()
                    snackBarHostState.showSnackbar(event.message.asString(context))
                }

                UiEvent.Success -> {
                    onSuccess()
                    snackBarHostState.currentSnackbarData?.dismiss()
                    snackBarHostState.showSnackbar(context.getString(R.string.sign_up_success))
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
        modifier = Modifier
            .fillMaxSize()
            .background(silverBackgroundBrush()),
        contentAlignment = Alignment.Center
    ) {
        BoxWithAnimatedBorder(
            modifier = Modifier
                .width(300.dp)
                .height(350.dp)
        ) {
            Column(
                modifier = Modifier

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
                    label = { Text(text = stringResource(id = R.string.enter_email_label)) },
                    colors = texFieldColors_1()
                )
                PasswordTextField(
                    password = state.password,
                    isPasswordVisible = state.isPasswordVisible,
                    onPasswordEntered = { onEvent(SignUpScreenEvent.OnPasswordEntered(it)) },
                    onShowPasswordClick = { onEvent(SignUpScreenEvent.OnShowPasswordClick) },
                    labelText = stringResource(id = R.string.enter_password_label),
                    modifier = Modifier.padding(bottom = 12.dp),
                    colors = texFieldColors_1()
                )
                PasswordTextField(
                    password = state.repeatedPassword,
                    isPasswordVisible = state.isPasswordVisible,
                    onPasswordEntered = { onEvent(SignUpScreenEvent.OnRepeatedPasswordEntered(it)) },
                    onShowPasswordClick = { onEvent(SignUpScreenEvent.OnShowPasswordClick) },
                    labelText = stringResource(id = R.string.repeat_password_label),
                    isError = state.password != state.repeatedPassword,
                    modifier = Modifier.padding(bottom = 12.dp),
                    colors = texFieldColors_1()
                )
                AuthButton(
                    buttonText = stringResource(id = R.string.sign_up),
                    enabled = state.isSignUpButtonEnabled,
                    modifier = Modifier.width(150.dp),
                    onClick = { onEvent(SignUpScreenEvent.OnSignUpClick) })
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun SignUpScreenPreview() {
    SignUpScreen(SignUpScreenState()) {}
}