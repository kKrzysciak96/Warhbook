package com.eltescode.user_presentation.utils

import com.eltescode.core_ui.utils.UiEvent

fun handleUiEvents(event: UiEvent, onNextScreen: (String) -> Unit, onSuccess: () -> Unit) {

    when (event) {

        UiEvent.OnNavigateUp -> Unit

        is UiEvent.OnNextScreen -> {
            event.route?.let { onNextScreen(it) }
        }

        is UiEvent.ShowSnackBar -> {}
        UiEvent.Success -> {
            onSuccess()
        }
    }
}