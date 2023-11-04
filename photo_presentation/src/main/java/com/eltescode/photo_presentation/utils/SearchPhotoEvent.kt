package com.eltescode.photo_presentation.utils

sealed interface SearchPhotoEvent {
    object OnPhotoSearch : SearchPhotoEvent
    data class OnQueryEntered(val query: String) : SearchPhotoEvent
    data class OnSetProfilePhoto(val photoUrl: String) : SearchPhotoEvent
    data class OnPhotoClick(val photoUrl: String) : SearchPhotoEvent
    object OnPhotoDialogDismiss : SearchPhotoEvent
}