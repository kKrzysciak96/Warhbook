package com.eltescode.notes_data.repository

import com.eltescode.notes_domain.model.Note

object SyncHelperImpl : SyncHelper {

    override fun compareData(localData: List<Note>, remoteData: List<Note>): List<Note> {
        val newList: MutableList<Note> = mutableListOf()

        localData.forEach { localNote ->
            val remoteNote = remoteData.firstOrNull { localNote.noteId == it.noteId }
            if (remoteNote != null) {
                if (localNote.timestamp > remoteNote.timestamp) {
                    newList.add(localNote)
                } else {
                    newList.add(remoteNote)
                }
            } else {
                newList.add(localNote)
            }
        }
        remoteData.forEach { remoteNote ->
            val localNote = localData.firstOrNull { remoteNote.noteId == it.noteId }
            if (localNote != null) {
                if (remoteNote.timestamp > localNote.timestamp) {
                    newList.add(remoteNote)
                } else {
                    newList.add(localNote)
                }
            } else {
                newList.add(remoteNote)
            }
        }

        return newList.distinct()
    }

}