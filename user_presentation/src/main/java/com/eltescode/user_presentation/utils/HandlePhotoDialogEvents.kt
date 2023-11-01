package com.eltescode.user_presentation.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.core.content.FileProvider
import androidx.work.WorkManager
import java.io.File

fun handlePhotoDialogEvents(
    context: Context,
    event: UserDataScreenEvent.PhotoDialogEvents,
    selectPhotoFromAlbumContract: ManagedActivityResultLauncher<Array<String>, Uri?>,
    takePhotoContract: ManagedActivityResultLauncher<Uri, Boolean>,
    onEvent: (UserDataScreenEvent) -> Unit,
    workManager: WorkManager

) {
    when (event) {
        UserDataScreenEvent.PhotoDialogEvents.OnChooseFromAlbumClick -> {
            selectPhotoFromAlbumContract.launch(arrayOf("image/*"))
        }

        UserDataScreenEvent.PhotoDialogEvents.OnChooseFromInternetClick -> {

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com")).apply {
            }
            context.startActivity(intent)
        }

        UserDataScreenEvent.PhotoDialogEvents.OnTakePhotoClick -> {
            val photoFile =
                File(context.filesDir, "IMG_${System.currentTimeMillis()}.JPG")
            val photoUri =
                FileProvider.getUriForFile(
                    context,
                    "com.eltescode.warhbook_custom_file_provider",
                    photoFile
                )
            onEvent(UserDataScreenEvent.OnFileUriCreated(photoUri))
            takePhotoContract.launch(photoUri)
        }

        is UserDataScreenEvent.PhotoDialogEvents.OnWorkManagerEnqueue -> {
            workManager.enqueue(event.request)
        }

        UserDataScreenEvent.PhotoDialogEvents.OnChooseFromFlickr -> {

        }
    }
}