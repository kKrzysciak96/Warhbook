package com.eltescode.notes_data.mappers

import com.eltescode.notes_data.local.NoteCached
import com.eltescode.notes_domain.model.Note

fun Note.mapToNoteCached() = NoteCached(
    id = id,
    title = title,
    content = content,
    timestamp = timestamp,
    color = color,
    attachment = attachment,
)

fun NoteCached.mapToNote() = Note(
    id = id,
    title = title,
    content = content,
    timestamp = timestamp,
    color = color,
    attachment = attachment,
)


