package com.eltescode.user_domain.repository

import com.eltescode.core_domain.utils.Result
import com.eltescode.user_domain.model.CustomUserData

interface UserDataRepository {
    fun signOut(): Result

    fun getCurrentUser(): CustomUserData?
}

