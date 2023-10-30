package com.eltescode.auth_domain.authenticator

import com.eltescode.auth_domain.model.CustomUser

interface Authenticator<T> {

    suspend fun signIn(credentials: T)
    suspend fun signUp(credentials: T)

    fun signOut()
    fun getCurrentUser(): CustomUser?
}


