package com.eltescode.core_ui.utils

sealed interface UiEvent {
    object Success : UiEvent
    object OnNavigateUp : UiEvent
    object OnNextScreen : UiEvent

    data class ShowSnackBar(val message: UiText) : UiEvent
}
