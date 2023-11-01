package com.eltescode.auth_presentation.sign_in_screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.eltescode.auth_presentation.components.AuthButton
import com.eltescode.auth_presentation.components.PasswordTextField
import com.eltescode.auth_presentation.utils.SignInScreenEvent
import com.eltescode.auth_presentation.utils.SignInScreenState
import com.eltescode.core_ui.R
import com.eltescode.core_ui.ui.fontFamily_croissant
import com.eltescode.core_ui.utils.UiEvent

@Composable
fun SignInScreen(
    snackBarHostState: SnackbarHostState,
    viewModel: SignInViewModel = hiltViewModel(),
    onSuccess: () -> Unit,
    onNextScreen: () -> Unit,
) {
    val state = viewModel.state
    val currentUser = remember { viewModel.currentUser }
    val context = LocalContext.current

    LaunchedEffect(key1 = currentUser, block = {
        if (viewModel.currentUser != null) {
            viewModel.onEvent(SignInScreenEvent.UserAlreadySignedIn)
        }
    })

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                UiEvent.OnNavigateUp -> {

                }

                is UiEvent.ShowSnackBar -> {
                    snackBarHostState.currentSnackbarData?.dismiss()
                    snackBarHostState.showSnackbar(event.message.asString(context))
                }

                UiEvent.Success -> {
                    onSuccess()
                    snackBarHostState.currentSnackbarData?.dismiss()
                    snackBarHostState.showSnackbar(context.getString(R.string.sign_in_success))
                }

                is UiEvent.OnNextScreen -> {
                    onNextScreen()
                }
            }
        }
    }

    SignInScreen(
        state = state,
        onEvent = viewModel::onEvent,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    state: SignInScreenState,
    onEvent: (SignInScreenEvent) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {
                onEvent(SignInScreenEvent.AdminOnSignInClick)
            },
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Text(text = "Admin login")
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
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
                        text = stringResource(id = R.string.login_header),
                        fontFamily = fontFamily_croissant,
                        fontSize = 32.sp,
                        modifier = Modifier
                    )
                    TextField(
                        value = state.email,
                        onValueChange = { onEvent(SignInScreenEvent.OnEmailEntered(it)) },
                        modifier = Modifier.padding(bottom = 12.dp),
                        label = { Text(text = stringResource(id = R.string.enter_email_label)) })
                    PasswordTextField(
                        password = state.password,
                        isPasswordVisible = state.isPasswordVisible,
                        onPasswordEntered = { onEvent(SignInScreenEvent.OnPasswordEntered(it)) },
                        onShowPasswordClick = { onEvent(SignInScreenEvent.OnShowPasswordClick) },
                        labelText = stringResource(id = R.string.enter_password_label),
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    AuthButton(
                        buttonText = stringResource(id = R.string.sign_in),
                        enabled = state.isSignUpButtonEnabled,
                        modifier = Modifier.width(150.dp),
                        onClick = { onEvent(SignInScreenEvent.OnSignInClick) })
                }
            }
            GoToSignUpScreenRow(
                modifier =
                if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE)
                    Modifier.fillMaxWidth()
                else
                    Modifier
                        .fillMaxWidth()
                        .padding(48.dp),
                onEvent = onEvent
            )
        }
    }
}


@Composable
private fun GoToSignUpScreenRow(
    modifier: Modifier = Modifier,
    onEvent: (SignInScreenEvent) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.ArrowRight,
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .padding(start = 16.dp, end = 16.dp)
        )
        AuthButton(
            buttonText = stringResource(id = R.string.sign_up),
            enabled = true,
            fontSize = 12.sp,
            modifier = Modifier.width(100.dp),
            onClick = { onEvent(SignInScreenEvent.OnGoToSignUpScreenClick) })

        Icon(
            imageVector = Icons.Default.ArrowLeft,
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .padding(start = 16.dp, end = 16.dp)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SignInScreenPreview() {
    SignInScreen(SignInScreenState()) {}
}




