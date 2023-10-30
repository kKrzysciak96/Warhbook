package com.eltescode.auth_domain.repository


import com.eltescode.auth_domain.model.CustomUser
import com.eltescode.core_domain.utils.Result
interface AuthRepository {
    suspend fun <T> signIn(credentials: T): Result
    suspend fun <T> signUp(credentials: T): Result

    fun signOut(): Result
    fun getCurrentUser(): CustomUser?
}