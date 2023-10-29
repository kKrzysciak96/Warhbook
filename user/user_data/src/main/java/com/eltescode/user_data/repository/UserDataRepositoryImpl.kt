package com.eltescode.user_data.repository

import com.eltescode.core_domain.utils.Result
import com.eltescode.user_domain.model.CustomUserData
import com.eltescode.user_domain.repository.UserDataRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class UserDataRepositoryImpl(private val auth: FirebaseAuth) : UserDataRepository {
    override fun signOut(): Result {
        return try {
            auth.signOut()
            Result.Success
        } catch (e: Exception) {
            Result.Error(e.message)
        }
    }

    override fun getCurrentUser(): CustomUserData? {
        return auth.currentUser?.mapToUserData()
    }
}

private fun FirebaseUser.mapToUserData() = CustomUserData(
    uid = uid,
    email = email,
    photo = photoUrl.toString(),
)