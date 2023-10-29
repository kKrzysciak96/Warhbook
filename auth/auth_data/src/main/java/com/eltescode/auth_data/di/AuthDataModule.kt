package com.eltescode.auth_data.di

import com.eltescode.auth_data.authenticators.FirebaseEmailAndPasswordAuthenticator
import com.eltescode.auth_data.repository.AuthRepositoryImpl
import com.eltescode.auth_domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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
    fun provideFirebaseEmailAndPasswordAuthenticator(auth: FirebaseAuth): FirebaseEmailAndPasswordAuthenticator {
        return FirebaseEmailAndPasswordAuthenticator(auth)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseEmailAndPasswordAuthenticator: FirebaseEmailAndPasswordAuthenticator,
        cloud: FirebaseFirestore,
    ): AuthRepository {
        return AuthRepositoryImpl(firebaseEmailAndPasswordAuthenticator, cloud)
    }
}