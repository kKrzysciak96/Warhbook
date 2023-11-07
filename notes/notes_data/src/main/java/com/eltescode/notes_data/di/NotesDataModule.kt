package com.eltescode.notes_data.di

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.eltescode.core_data.utils.exception.ErrorWrapper
import com.eltescode.core_data.utils.exception.ErrorWrapperImpl
import com.eltescode.core_data.utils.network.NetworkStateProvider
import com.eltescode.core_data.utils.network.NetworkStateProviderImpl
import com.eltescode.notes_data.local.NoteDatabase
import com.eltescode.notes_data.repository.NoteRepositoryImpl
import com.eltescode.notes_data.repository.SyncHelper
import com.eltescode.notes_data.repository.SyncHelperImpl
import com.eltescode.notes_data.utils.CustomPreferences
import com.eltescode.notes_data.utils.CustomPreferencesImpl
import com.eltescode.notes_domain.repository.NoteRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotesDataModule {

    @Provides
    @Singleton
    fun providesNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATA_BASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providesConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Provides
    @Singleton
    fun providesSyncHelper(): SyncHelper {
        return SyncHelperImpl
    }

    @Provides
    @Singleton
    fun providesNetworkStateProvider(connectivityManager: ConnectivityManager): NetworkStateProvider {
        return NetworkStateProviderImpl(connectivityManager)
    }


    @Provides
    @Singleton
    fun providesErrorWrapper(): ErrorWrapper {
        return ErrorWrapperImpl()
    }

    @Provides
    @Singleton
    fun providesErrorCoroutineScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob())
    }

    @Provides
    @Singleton
    fun providesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create { context.preferencesDataStoreFile("data_store_pref") }
    }

    @Provides
    @Singleton
    fun providesCustomPreferences(dataStore: DataStore<Preferences>): CustomPreferences {
        return CustomPreferencesImpl(dataStore)
    }

    @Provides
    @Singleton
    fun providesNoteRepository(
        db: NoteDatabase,
        auth: FirebaseAuth,
        fireStore: FirebaseFirestore,
        errorWrapper: ErrorWrapper,
        networkStateProvider: NetworkStateProvider,
        preferences: CustomPreferences,
        syncHelper: SyncHelper,
        coroutineScope: CoroutineScope
    ): NoteRepository {
        return NoteRepositoryImpl(
            db.provideDao(),
            errorWrapper,
            auth,
            fireStore,
            networkStateProvider,
            syncHelper,
            preferences,
            coroutineScope,
        )
    }


}