package com.eltescode.notes_domain.use_cases

import com.eltescode.notes_domain.repository.NoteRepository

class SyncDataUseCase(private val repository: NoteRepository) {

    suspend operator fun invoke() {
        return repository.syncNotes()
    }
}