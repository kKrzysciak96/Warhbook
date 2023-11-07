package com.eltescode.notes_domain.model


data class Note(
    val title: String = "",
    val content: String = "",
    val timestamp: Long = -1,
    val color: Int = -1,
    val noteId: String = "",
    val attachment: String = "",
)