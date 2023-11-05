package com.eltescode.notes_presentation.util

import com.eltescode.core_ui.navigation.Routes

sealed class NoteScreen(val route: String) {
    object NotesNoteScreen : NoteScreen(Routes.NOTES)
    object AddEditNoteNoteScreen : NoteScreen(Routes.ADD_EDIT_NOTE)
}
