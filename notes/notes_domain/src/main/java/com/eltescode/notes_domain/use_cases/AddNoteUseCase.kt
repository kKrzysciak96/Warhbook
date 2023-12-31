package com.eltescode.notes_domain.use_cases

import com.eltescode.notes_domain.model.Note
import com.eltescode.notes_domain.repository.NoteRepository
import com.eltescode.notes_domain.repository.Result
import com.eltescode.notes_domain.utils.InvalidNoteException

class AddNoteUseCase(private val repository: NoteRepository) {

    suspend operator fun invoke(note: Note): Result {
        if (note.title.isBlank()) {
            throw InvalidNoteException("Title can not be blank")
        }
        return repository.insertNote(note)
    }
}