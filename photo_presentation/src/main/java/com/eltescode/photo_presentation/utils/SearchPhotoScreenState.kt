package com.eltescode.photo_presentation.utils

import com.eltescode.photo_domain.model.Photo

data class SearchPhotoScreenState(
    val photos: List<Photo> = emptyList(),
    val isSearching: Boolean = false,
    val query: String = "dog",
    val photoDialogState: PhotoDialogState = PhotoDialogState()
)

data class PhotoDialogState(
    val isDialogVisible: Boolean = false,
    val photoToSetProfilePicture: String = "xxx"
)
