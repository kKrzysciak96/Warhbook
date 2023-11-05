package com.eltescode.notes_presentation.model

import androidx.compose.ui.graphics.Color


data class NoteDisplayable(
    val id: Int? = null,
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    val attachment: String = ""
) {

    companion object {
        val noteColors = listOf(
            Color(0XFF5b39c6),
            Color(0XFFba160c),
            Color(0XFFe0af1f),
            Color(0XFF33cc5a),
            Color(0XFF8bbe1b)
        )
    }
}