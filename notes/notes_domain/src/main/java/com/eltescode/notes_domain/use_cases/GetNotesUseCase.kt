package com.eltescode.notes_domain.use_cases

import com.eltescode.notes_domain.model.Note
import com.eltescode.notes_domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow


class GetNotesUseCase(private val repository: NoteRepository) {

    suspend operator fun invoke(): Flow<List<Note>> {
        return repository.getAllNotes()
    }
}
