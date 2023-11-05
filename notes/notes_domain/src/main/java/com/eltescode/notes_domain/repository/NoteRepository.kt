package com.eltescode.notes_domain.repository

import com.eltescode.notes_domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getAllNotes(): Flow<List<Note>>

    suspend fun getNoteById(id: Int): Note?

    suspend fun insertNote(noteDomain: Note)

    suspend fun deleteNote(noteDomain: Note)
}