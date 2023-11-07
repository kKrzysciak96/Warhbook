package com.eltescode.notes_data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NoteCached::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun provideDao(): NoteDao

    companion object {
        const val DATA_BASE_NAME = "notes_data_base"
    }
}