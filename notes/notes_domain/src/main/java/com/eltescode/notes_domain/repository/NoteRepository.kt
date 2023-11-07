package com.eltescode.notes_domain.repository

import com.eltescode.notes_domain.model.Note
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface NoteRepository {

    suspend fun getAllNotes(): Flow<List<Note>>

    suspend fun getNoteById(id: UUID): Note?

    suspend fun insertNote(note: Note): Result

    suspend fun deleteNote(note: Note): Result

    suspend fun isNeededToSync(): Boolean

    suspend fun syncNotes()
}

sealed interface Result {
    object SuccessRemote : Result
    object SuccessLocal : Result
    data class Error(val message: String?) : Result
}