package com.eltescode.notes_domain.use_cases

import com.eltescode.notes_domain.model.Note
import com.eltescode.notes_domain.repository.NoteRepository

class DeleteNoteUseCase(private val repository: NoteRepository) {

    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note)
    }
}