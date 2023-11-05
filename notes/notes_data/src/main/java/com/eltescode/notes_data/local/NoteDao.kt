package com.eltescode.notes_data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM note_table")
    fun getAllNotes(): Flow<List<NoteCached>>

    @Query("SELECT * FROM note_table WHERE id = :id")
    suspend fun getNoteById(id: Int): NoteCached?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(noteDomain: NoteCached)

    @Delete
    suspend fun deleteNote(noteDomain: NoteCached)
}