package com.eltescode.auth_presentation.utils


data class SignInScreenState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false
) {
    val isSignUpButtonEnabled
        get() = password.isNotBlank() && email.isNotBlank()
}