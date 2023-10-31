package com.eltescode.user_presentation.di

import com.eltescode.user_presentation.utils.UriHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UserPresentationModule {

    @Provides
    @Singleton
    fun provideUriHelper(): UriHelper = UriHelper()
}