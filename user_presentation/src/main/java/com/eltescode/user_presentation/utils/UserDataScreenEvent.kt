package com.eltescode.user_presentation.utils

sealed interface UserDataScreenEvent {
    object OnSignOut : UserDataScreenEvent
}