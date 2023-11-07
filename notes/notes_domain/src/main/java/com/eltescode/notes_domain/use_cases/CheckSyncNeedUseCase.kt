package com.eltescode.notes_domain.use_cases

import com.eltescode.notes_domain.repository.NoteRepository

class CheckSyncNeedUseCase(private val repository: NoteRepository) {

    suspend operator fun invoke(): Boolean {
        return repository.isNeededToSync()
    }
}