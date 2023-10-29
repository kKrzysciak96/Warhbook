package com.eltescode.auth_domain.repository

import com.eltescode.auth_domain.authenticator.Result
import com.eltescode.auth_domain.model.CustomUser

interface AuthRepository {
    suspend fun <T> signIn(credentials: T): Result
    suspend fun <T> signUp(credentials: T): Result

    fun signOut(): Result
    fun getCurrentUser(): CustomUser?
}