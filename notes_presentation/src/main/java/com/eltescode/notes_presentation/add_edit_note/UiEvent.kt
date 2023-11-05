package com.eltescode.notes.features.note.presentation.add_edit_note

sealed class UiEvent {
    data class ShowSnackBar(val message: String) : UiEvent()
    object SaveNote : UiEvent()
}