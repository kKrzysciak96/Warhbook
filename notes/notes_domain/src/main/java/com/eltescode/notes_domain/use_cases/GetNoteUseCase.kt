package com.eltescode.notes_domain.use_cases

import com.eltescode.notes_domain.model.Note
import com.eltescode.notes_domain.repository.NoteRepository
import java.util.UUID

class GetNoteUseCase(private val repository: NoteRepository) {

    suspend operator fun invoke(id: UUID): Note? {
        return repository.getNoteById(id)
    }
}