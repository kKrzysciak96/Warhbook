package com.eltescode.photo_domain.di

import com.eltescode.photo_domain.repository.PhotoRepository
import com.eltescode.photo_domain.use_cases.SearchPhotosUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PhotoDomainModule {

    @Provides
    @Singleton
    fun provideSearchPhotoUseCase(repository: PhotoRepository): SearchPhotosUseCase {
        return SearchPhotosUseCase(repository)
    }
}