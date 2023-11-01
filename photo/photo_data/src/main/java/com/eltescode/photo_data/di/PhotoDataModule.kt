package com.eltescode.photo_data.di

import com.eltescode.photo_data.remote.FlickrApi
import com.eltescode.photo_data.remote.PhotoInterceptor
import com.eltescode.photo_data.repository.PhotoRepositoryImpl
import com.eltescode.photo_domain.repository.PhotoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PhotoDataModule {

    @Provides
    @Singleton
    fun provideInterceptor(): Interceptor {
        return PhotoInterceptor()
    }

    @Provides
    @Singleton
    fun provideClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Provides
    @Singleton
    fun provideApi(client: OkHttpClient): FlickrApi {
        return Retrofit
            .Builder()
            .baseUrl(FlickrApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideSearchPhotoUseCase(api: FlickrApi): PhotoRepository {
        return PhotoRepositoryImpl(api)
    }
}