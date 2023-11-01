package com.eltescode.photo_data.repository

import com.eltescode.photo_data.mappers.mapToPhoto
import com.eltescode.photo_data.remote.FlickrApi
import com.eltescode.photo_domain.model.Photo
import com.eltescode.photo_domain.repository.PhotoRepository

class PhotoRepositoryImpl(private val api: FlickrApi) : PhotoRepository {
    override suspend fun searchPhotos(query: String): Result<List<Photo>> {
        return try {
            val result = api.searchPhotos(query).photos.photoList.map { it.mapToPhoto() }
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}