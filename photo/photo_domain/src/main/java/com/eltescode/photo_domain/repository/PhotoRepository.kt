package com.eltescode.photo_domain.repository

import com.eltescode.photo_domain.model.Photo

interface PhotoRepository {
    suspend fun searchPhotos(query: String): Result<List<Photo>>
}