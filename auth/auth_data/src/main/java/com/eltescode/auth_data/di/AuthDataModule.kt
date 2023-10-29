package com.eltescode.auth_data.di

import com.eltescode.auth_data.authenticators.FirebaseEmailAndPasswordAuthenticator
import com.eltescode.auth_data.repository.AuthRepositoryImpl
import com.eltescode.auth_domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthDataModule {

    @Provides
    @Singleton
    fun provideFireBaseAuth(): FirebaseAuth {
        return Firebase.auth
    }

    @Provides
    @Singleton
    fun provideFireBaseStorage(): FirebaseStorage {
        return Firebase.storage
    }

    @Provides
    @Singleton
    fun provideFireBaseFireStore(): FirebaseFirestore {
        return Firebase.firestore
    }

    @Provides
    @Singleton
    fun provideFirebaseEmailAndPasswordAuthenticator(auth: FirebaseAuth): FirebaseEmailAndPasswordAuthenticator {
        return FirebaseEmailAndPasswordAuthenticator(auth)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseEmailAndPasswordAuthenticator: FirebaseEmailAndPasswordAuthenticator,
        storage: FirebaseStorage,
        cloud: FirebaseFirestore,
    ): AuthRepository {
        return AuthRepositoryImpl(firebaseEmailAndPasswordAuthenticator, cloud, storage)
    }
}