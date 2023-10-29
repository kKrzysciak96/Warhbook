package com.eltescode.auth_domain.use_cases

class PasswordsValidator {
    operator fun invoke(password: String, repeatedPassword: String): PasswordResult {
        return when {
            password.isBlank() -> PasswordResult.BlankPassword
            password != repeatedPassword -> PasswordResult.DifferentPasswords
            else -> PasswordResult.Success
        }
    }
}

sealed interface PasswordResult {
    object Success : PasswordResult
    object BlankPassword : PasswordResult
    object DifferentPasswords : PasswordResult
}
//sealed class PasswordExceptions(message: String) : Throwable(message){
//    object BlankPassword : PasswordExceptions("Password can not be empty")
//    object DifferentPasswords : PasswordExceptions("Passwords are not the same")
//}