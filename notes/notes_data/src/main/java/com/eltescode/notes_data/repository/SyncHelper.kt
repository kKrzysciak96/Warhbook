package com.eltescode.notes_data.repository

import com.eltescode.notes_domain.model.Note

interface SyncHelper {
    fun compareData(localData: List<Note>, remoteData: List<Note>): List<Note>

}

