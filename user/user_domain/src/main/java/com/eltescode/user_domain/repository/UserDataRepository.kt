package com.eltescode.user_domain.repository

import com.eltescode.core_domain.utils.Result
import com.eltescode.user_domain.model.CustomUserData

interface UserDataRepository {
    fun signOut(): Result

    suspend fun editUserPersonalData(newUserData: CustomUserData): Result

    suspend fun getUserData(): kotlin.Result<CustomUserData>

    suspend fun uploadUserPhoto(bytes: ByteArray): kotlin.Result<UserPhotoUrl>
}

typealias UserPhotoUrl = String?
typealias CurrentUserId = String?
