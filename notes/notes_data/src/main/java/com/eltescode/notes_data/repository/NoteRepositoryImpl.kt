package com.eltescode.notes_data.repository

import com.eltescode.notes_data.local.NoteDao
import com.eltescode.notes_data.mappers.mapToNote
import com.eltescode.notes_data.mappers.mapToNoteCached
import com.eltescode.notes_domain.model.Note
import com.eltescode.notes_domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(private val dao: NoteDao) : NoteRepository {

    override fun getAllNotes(): Flow<List<Note>> {
        return dao.getAllNotes().map { list -> list.map { it.mapToNote() } }
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)?.mapToNote()
    }

    override suspend fun insertNote(noteDomain: Note) {
        dao.insertNote(noteDomain.mapToNoteCached())
    }

    override suspend fun deleteNote(noteDomain: Note) {
        dao.deleteNote(noteDomain.mapToNoteCached())
    }
}