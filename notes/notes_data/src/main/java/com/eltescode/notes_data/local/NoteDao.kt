package com.eltescode.notes_data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface NoteDao {

    @Query("SELECT * FROM note_table")
    fun getAllNotesFlow(): Flow<List<NoteCached>>

    @Query("SELECT * FROM note_table")
    suspend fun getAllNotes(): List<NoteCached>

    @Query("SELECT * FROM note_table WHERE noteId = :id")
    suspend fun getNoteById(id: UUID): NoteCached?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(vararg noteDomain: NoteCached)

    @Delete
    suspend fun deleteNote(noteDomain: NoteCached)

    @Query("DELETE FROM note_table")
    suspend fun dropDataBase()
}