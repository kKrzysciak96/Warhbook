package com.eltescode.notes_presentation.add_edit_note

import com.eltescode.core_ui.utils.UiText


data class NoteTextFieldState(
    val text: String = "",
    val hint: UiText,
    val isHintVisible: Boolean = true
)
