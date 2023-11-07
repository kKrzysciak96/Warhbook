package com.eltescode.user_presentation.utils

import android.net.Uri
import com.eltescode.user_domain.model.CustomUserData

data class UserScreenState(
    val userData: CustomUserData?,
    val isSettingsDialogVisible: Boolean = false,
    val isChoosePhotoDialogVisible: Boolean = false,
    val isPhotoLoading: Boolean = false,
    val photoUri: Uri? = null,
)


