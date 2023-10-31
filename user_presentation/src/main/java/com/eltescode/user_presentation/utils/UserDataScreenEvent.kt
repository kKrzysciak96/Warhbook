package com.eltescode.user_presentation.utils

import android.net.Uri

sealed interface UserDataScreenEvent {
    object OnSignOut : UserDataScreenEvent
    object OnSettingsClick : UserDataScreenEvent
    object OnSettingsDialogDismiss : UserDataScreenEvent
    object OnSettingsSave : UserDataScreenEvent
    object OnPhotoClick : UserDataScreenEvent
    sealed interface PhotoDialogEvents : UserDataScreenEvent {

        object OnTakePhotoClick : PhotoDialogEvents
        object OnChooseFromAlbumClick : PhotoDialogEvents
        object OnChooseFromInternetClick : PhotoDialogEvents
        object OnChooseFromFlickr : PhotoDialogEvents
    }

    data class OnFileUriCreated(val uri: Uri) : UserDataScreenEvent
    object OnPhotoDialogDismiss : UserDataScreenEvent
    data class OnUploadPhoto(val bytes: ByteArray) : UserDataScreenEvent
    data class OnNameEntered(val name: String) : UserDataScreenEvent
    data class OnSurnameEntered(val surname: String) : UserDataScreenEvent
    data class OnNextScreenClick(val route: String) : UserDataScreenEvent
}