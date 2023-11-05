package com.eltescode.notes_presentation.mappers

import com.eltescode.notes_domain.model.Note
import com.eltescode.notes_presentation.model.NoteDisplayable

fun Note.mapToNoteDisplayable() = NoteDisplayable(
    id = id,
    title = title,
    content = content,
    timestamp = timestamp,
    color = color,
    attachment = attachment,
)

fun NoteDisplayable.mapToNote() = Note(
    id = id,
    title = title,
    content = content,
    timestamp = timestamp,
    color = color,
    attachment = attachment,
)


