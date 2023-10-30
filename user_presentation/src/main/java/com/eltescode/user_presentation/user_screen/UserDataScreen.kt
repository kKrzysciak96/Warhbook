package com.eltescode.user_presentation.user_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.eltescode.core_ui.components.SignOutIcon
import com.eltescode.core_ui.utils.UiEvent
import com.eltescode.user_domain.model.CustomUserData
import com.eltescode.user_presentation.utils.UserDataScreenEvent

@Composable
fun UserDataScreen(
    viewModel: UserDataViewModel = hiltViewModel(),
    onSuccess: () -> Unit
) {
    val currentUser = remember { viewModel.currentUser }

    LaunchedEffect(true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                UiEvent.OnNavigateUp -> Unit
                UiEvent.OnNextScreen -> {}
                is UiEvent.ShowSnackBar -> {}
                UiEvent.Success -> {
                    onSuccess()
                }
            }
        }
    }
    UserDataScreen(currentUser = currentUser, viewModel::onEvent)
}

@Composable
fun UserDataScreen(currentUser: CustomUserData?, onEvent: (UserDataScreenEvent) -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        SignOutIcon(
            contentDescription = null,
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            onEvent(UserDataScreenEvent.OnSignOut)
        }

        Text(text = currentUser.toString())
    }
}

@Preview
@Composable
fun UserDataScreen() {

}