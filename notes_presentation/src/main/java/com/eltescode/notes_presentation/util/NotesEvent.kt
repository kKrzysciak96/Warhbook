package com.eltescode.notes_presentation.util

import com.eltescode.notes_domain.utils.NoteOrder
import com.eltescode.notes_presentation.model.NoteDisplayable

sealed interface NotesEvent {
    data class Order(val noteOrder: NoteOrder) : NotesEvent
    data class DeleteNote(val note: NoteDisplayable) : NotesEvent
    object RestoreNote : NotesEvent
    object ToggleOrderSection : NotesEvent
    data class OnAddEditNote(val id: Int?, val color: Int?) : NotesEvent


}
