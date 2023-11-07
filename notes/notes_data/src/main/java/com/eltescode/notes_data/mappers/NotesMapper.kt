package com.eltescode.notes_data.mappers

import com.eltescode.notes_data.local.NoteCached
import com.eltescode.notes_domain.model.Note
import java.util.UUID

fun Note.mapToNoteCached() = NoteCached(
    noteId = UUID.fromString(noteId),
    title = title,
    content = content,
    timestamp = timestamp,
    color = color,
    attachment = attachment,
)

fun NoteCached.mapToNote() = Note(
    noteId = noteId.toString(),
    title = title,
    content = content,
    timestamp = timestamp,
    color = color,
    attachment = attachment,
)


