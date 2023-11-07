package com.eltescode.notes_data.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first


class CustomPreferencesImpl(
    private val dataStore: DataStore<Preferences>
) : CustomPreferences {
    override suspend fun setSyncInfo(syncInfo: Boolean) {
        dataStore.edit {
            it[SYNC_INFO] = syncInfo
        }
    }

    override suspend fun getSyncInfo(): Boolean {

        return dataStore.data.first()[SYNC_INFO] ?: true
    }

    companion object {
        private val SYNC_INFO = booleanPreferencesKey("SYNC_INFO")
    }
}