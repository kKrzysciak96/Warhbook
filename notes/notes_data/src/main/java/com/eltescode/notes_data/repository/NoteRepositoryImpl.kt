package com.eltescode.notes_data.repository

import com.eltescode.core_data.utils.exception.ErrorWrapper
import com.eltescode.core_data.utils.exception.callOrThrow
import com.eltescode.core_data.utils.network.NetworkStateProvider
import com.eltescode.notes_data.local.NoteDao
import com.eltescode.notes_data.mappers.mapToNote
import com.eltescode.notes_data.mappers.mapToNoteCached
import com.eltescode.notes_data.utils.CustomPreferences
import com.eltescode.notes_domain.model.Note
import com.eltescode.notes_domain.repository.NoteRepository
import com.eltescode.notes_domain.repository.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import java.util.UUID

class NoteRepositoryImpl(
    private val dao: NoteDao,
    private val errorWrapper: ErrorWrapper,
    private val auth: FirebaseAuth,
    private val fireStore: FirebaseFirestore,
    private val networkStateProvider: NetworkStateProvider,
    private val syncHelper: SyncHelper,
    private val preferences: CustomPreferences,
    private val externalScope: CoroutineScope,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : NoteRepository {

    override suspend fun syncNotes() {
        if (networkStateProvider.isNetworkAvailable()) {
            val remoteData = getAllNotesFromRemote()
            val localData = getAllNotesFromLocal()
            val areTheSame =
                remoteData.size == localData.size
                        && remoteData.containsAll(localData)
                        && localData.containsAll(remoteData)
            if (!areTheSame) {
                val dataToSync =
                    syncHelper.compareData(remoteData = remoteData, localData = localData)
                clearDataBase()
                insertNotesToLocal(dataToSync)
                insertNotesToRemote(dataToSync)
            }
            preferences.setSyncInfo(false)
        }

    }

    override suspend fun getAllNotes(): Flow<List<Note>> {
        return if (networkStateProvider.isNetworkAvailable()) {
            callOrThrow(errorWrapper) {
                val remoteData = getAllNotesFromRemote()
                flow { emit(remoteData) }
            }
        } else {
            getAllNotesFromLocalFlow()
        }
    }

    override suspend fun insertNote(note: Note): Result {
        return if (networkStateProvider.isNetworkAvailable()) {
            insertNoteToRemote(note).also { result ->
                if (result == Result.SuccessRemote) insertNoteToLocal(note)
            }
        } else {
            preferences.setSyncInfo(true)
            insertNoteToLocal(note)
            Result.SuccessLocal
        }
    }

    override suspend fun getNoteById(id: UUID): Note? {
        return getNoteByIdFromLocal(id)
    }

    override suspend fun deleteNote(note: Note): Result {

        return if (networkStateProvider.isNetworkAvailable()) {
            deleteNoteFromRemote(note).also { result ->
                if (result == Result.SuccessRemote) deleteNoteFromLocal(note)
            }

        } else {
            preferences.setSyncInfo(true)
            deleteNoteFromLocal(note)
            Result.SuccessLocal
        }
    }

    override suspend fun isNeededToSync(): Boolean {
        return preferences.getSyncInfo()
    }

    private suspend fun getAllNotesFromRemote(): List<Note> {

        val currentUserId = auth.currentUser?.uid ?: throw Exception("No such user")
        val result = fireStore.collection("users")
            .document(currentUserId)
            .collection("notes")
            .get()
            .await()

        return result.toObjects(Note::class.java)

    }

    private fun getAllNotesFromLocalFlow(): Flow<List<Note>> {
        return dao.getAllNotesFlow().map { list -> list.map { it.mapToNote() } }
    }

    private suspend fun getAllNotesFromLocal(): List<Note> {
        return dao.getAllNotes().map { list -> list.mapToNote() }
    }

    private suspend fun getNoteByIdFromLocal(id: UUID): Note? {
        return dao.getNoteById(id)?.mapToNote()
    }

    private suspend fun insertNoteToRemote(note: Note): Result {
        return try {
            val currentUserId = auth.currentUser?.uid ?: throw Exception("No such user")
            val currentNoteId = note.noteId
            fireStore.collection("users")
                .document(currentUserId)
                .collection("notes")
                .document("Note $currentNoteId")
                .set(note)
                .await()

            Result.SuccessRemote
        } catch (e: Exception) {
            Result.Error(e.message)
        }
    }

    private suspend fun insertNotesToRemote(notes: List<Note>) {
        notes.forEach {
            insertNoteToRemote(it)
        }
    }

    private suspend fun insertNoteToLocal(note: Note) {
        dao.insertNote(note.mapToNoteCached())
    }

    private suspend fun insertNotesToLocal(notes: List<Note>) {
        notes
            .map { it.mapToNoteCached() }
            .toTypedArray()
            .let { dao.insertNote(*it) }
    }

    private suspend fun deleteNoteFromRemote(note: Note): Result {
        return try {
            val currentUserId = auth.currentUser?.uid ?: throw Exception("No such user")
            val currentNoteId = note.noteId
            fireStore.collection("users")
                .document(currentUserId)
                .collection("notes")
                .document("Note $currentNoteId")
                .delete()
                .await()
            Result.SuccessRemote
        } catch (e: Exception) {
            Result.Error(e.message)
        }
    }

    private suspend fun deleteNoteFromLocal(noteDomain: Note) {
        dao.deleteNote(noteDomain.mapToNoteCached())
    }

    private suspend fun clearDataBase() {
        dao.dropDataBase()
    }

}