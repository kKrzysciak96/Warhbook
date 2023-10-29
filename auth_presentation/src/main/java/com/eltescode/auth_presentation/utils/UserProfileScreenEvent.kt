package com.eltescode.auth_presentation.utils

sealed interface UserProfileScreenEvent {
    object OnSignOut : UserProfileScreenEvent
}