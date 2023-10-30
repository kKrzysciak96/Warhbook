package com.eltescode.auth_presentation.utils

sealed interface SignInScreenEvent {
    data class OnEmailEntered(val email: String) : SignInScreenEvent
    data class OnPasswordEntered(val password: String) : SignInScreenEvent
    object OnSignInClick : SignInScreenEvent
    object AdminOnSignInClick : SignInScreenEvent
    object OnShowPasswordClick : SignInScreenEvent
    object OnGoToSignUpScreenClick : SignInScreenEvent
    object UserAlreadySignedIn : SignInScreenEvent

}