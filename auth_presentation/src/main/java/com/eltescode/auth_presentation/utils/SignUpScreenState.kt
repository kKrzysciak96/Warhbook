package com.eltescode.auth_presentation.utils

data class SignUpScreenState(
    val email: String = "",
    val password: String = "",
    val repeatedPassword: String = "",
    val isPasswordVisible: Boolean = false
) {
    val isSignUpButtonEnabled
        get() = password == repeatedPassword && email.isNotBlank() && password.isNotBlank()
}
