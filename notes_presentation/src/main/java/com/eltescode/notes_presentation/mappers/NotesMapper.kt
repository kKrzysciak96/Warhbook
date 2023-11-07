package com.eltescode.notes_presentation.mappers

import com.eltescode.notes_domain.model.Note
import com.eltescode.notes_presentation.model.NoteDisplayable
import java.util.UUID

fun Note.mapToNoteDisplayable() = NoteDisplayable(
    noteId = UUID.fromString(noteId),
    title = title,
    content = content,
    timestamp = timestamp,
    color = color,
    attachment = attachment,
)

fun NoteDisplayable.mapToNote() = Note(
    noteId = noteId.toString(),
    title = title,
    content = content,
    timestamp = timestamp,
    color = color,
    attachment = attachment,
)


