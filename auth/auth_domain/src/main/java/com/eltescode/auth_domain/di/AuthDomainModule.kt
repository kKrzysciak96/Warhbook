package com.eltescode.auth_domain.di

import com.eltescode.auth_domain.use_cases.PasswordsValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AuthDomainModule {

    @Provides
    @Singleton
    fun providePasswordsValidator(): PasswordsValidator {
        return PasswordsValidator()
    }
}