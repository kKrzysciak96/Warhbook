package com.eltescode.user_presentation.utils

import android.net.Uri
import kotlinx.coroutines.flow.MutableSharedFlow

object UriHelper {
    var oldUri: Uri? = null

    val uriFlow = MutableSharedFlow<Uri?>()
}

