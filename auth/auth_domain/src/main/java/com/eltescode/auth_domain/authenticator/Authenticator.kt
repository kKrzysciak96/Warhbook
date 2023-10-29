package com.eltescode.auth_domain.authenticator

import com.eltescode.auth_domain.model.CustomUser

interface Authenticator<T> {

    suspend fun signIn(credentials: T): Result
    suspend fun signUp(credentials: T): Result

    fun signOut(): Result
    fun getCurrentUser(): CustomUser?
}

sealed interface Result {
    object Success : Result
    data class Error(val message: String?) : Result
}
