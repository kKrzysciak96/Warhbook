package com.eltescode.core_ui.utils

import android.content.Context

sealed interface UiText {
    data class StringResource(val stringResId: Int) : UiText
    data class DynamicString(val text: String) : UiText


    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> text
            is StringResource -> context.getString(stringResId)
        }
    }
}
