package com.eltescode.auth_presentation.utils

data class SignUpScreenState(
    val email: String = "",
    val password: String = "",
    val repeatedPassword: String = "",
    val isPasswordVisible: Boolean = false
) {
    val areThePasswordsTheSame = password == repeatedPassword
}
