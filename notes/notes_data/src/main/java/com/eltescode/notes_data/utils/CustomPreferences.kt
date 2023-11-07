package com.eltescode.notes_data.utils

interface CustomPreferences {

    suspend fun setSyncInfo(syncInfo: Boolean)

    suspend fun getSyncInfo(): Boolean
}