package com.eltescode.notes_domain.di

import com.eltescode.notes_domain.repository.NoteRepository
import com.eltescode.notes_domain.use_cases.AddNoteUseCase
import com.eltescode.notes_domain.use_cases.CheckSyncNeedUseCase
import com.eltescode.notes_domain.use_cases.DeleteNoteUseCase
import com.eltescode.notes_domain.use_cases.GetNoteUseCase
import com.eltescode.notes_domain.use_cases.GetNotesUseCase
import com.eltescode.notes_domain.use_cases.NoteUseCases
import com.eltescode.notes_domain.use_cases.SortNotesUseCase
import com.eltescode.notes_domain.use_cases.SyncDataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotesDomainModule {
    @Provides
    @Singleton
    fun providesNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            deleteNoteUseCase = DeleteNoteUseCase(repository),
            getNoteUseCase = GetNoteUseCase(repository),
            addNoteUseCase = AddNoteUseCase(repository),
            sortNotesUseCase = SortNotesUseCase(),
            getNotesUseCase = GetNotesUseCase(repository),
            syncDataUseCase = SyncDataUseCase(repository),
            checkSyncNeedUseCase = CheckSyncNeedUseCase(repository)
        )
    }
}