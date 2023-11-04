package com.eltescode.photo_domain.use_cases

import com.eltescode.photo_domain.model.Photo
import com.eltescode.photo_domain.repository.PhotoRepository

class SearchPhotosUseCase(private val repository: PhotoRepository) {

    suspend operator fun invoke(
        query: String,
    ): Result<List<Photo>> {
        return if (query.isNotBlank()) {
            repository.searchPhotos(query.trim())

        } else {

            Result.success(emptyList())
        }
    }
}