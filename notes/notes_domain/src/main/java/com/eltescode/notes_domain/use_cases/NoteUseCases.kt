package com.eltescode.notes_domain.use_cases

data class NoteUseCases(
    val getNotesUseCase: GetNotesUseCase,
    val getNoteUseCase: GetNoteUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val addNoteUseCase: AddNoteUseCase,
    val sortNotesUseCase: SortNotesUseCase,
    val checkSyncNeedUseCase: CheckSyncNeedUseCase,
    val syncDataUseCase: SyncDataUseCase
)
