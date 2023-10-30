package com.eltescode.auth_presentation.utils

sealed interface SignUpScreenEvent {
    data class OnEmailEntered(val email: String) : SignUpScreenEvent
    data class OnPasswordEntered(val password: String) : SignUpScreenEvent
    data class OnRepeatedPasswordEntered(val repeatedPassword: String) : SignUpScreenEvent
    object OnSignUpClick : SignUpScreenEvent
    object OnShowPasswordClick : SignUpScreenEvent


}
