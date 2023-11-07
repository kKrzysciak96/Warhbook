package com.eltescode.notes_data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "note_table")
data class NoteCached(
    @PrimaryKey
    val noteId: UUID,
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    val attachment: String = "",
    val userId: UUID? = null,
) {


}