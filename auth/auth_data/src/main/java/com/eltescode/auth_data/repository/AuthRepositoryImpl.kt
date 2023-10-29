package com.eltescode.auth_data.repository

import com.eltescode.auth_data.authenticators.FirebaseEmailAndPasswordAuthenticator
import com.eltescode.auth_domain.model.CustomUser
import com.eltescode.auth_domain.repository.AuthRepository
import com.eltescode.auth_domain.utils.EmailAndPasswordCredentials
import com.eltescode.core_domain.utils.Result
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private val authenticator: FirebaseEmailAndPasswordAuthenticator,
    private val fireStore: FirebaseFirestore,
) : AuthRepository {
    override suspend fun <T> signIn(credentials: T): Result {
        return try {
            authenticator.signIn(credentials as EmailAndPasswordCredentials)
            Result.Success
        } catch (e: Exception) {
            Result.Error(e.message)
        }
    }

    override suspend fun <T> signUp(credentials: T): Result {
        return try {
            authenticator.signUp(credentials as EmailAndPasswordCredentials)
            authenticator.getCurrentUser()?.let {
                val newUser = CustomUser(uid = it.uid, email = it.email)
                createNewUser(newUser)
            }
            Result.Success
        } catch (e: Exception) {
            Result.Error(e.message)
        }
    }

    override fun signOut(): Result {
        return try {
            authenticator.signOut()
            Result.Success
        } catch (e: Exception) {
            Result.Error(e.message)
        }
    }

    override fun getCurrentUser(): CustomUser? {
        return authenticator.getCurrentUser()
    }

    private suspend fun createNewUser(newUser: CustomUser) {
        try {
            if (isUserValid(newUser)) {
                fireStore.collection("users").document(newUser.uid).set(newUser).await()
            } else {
                throw Exception("User id is empty string")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun isUserValid(user: CustomUser): Boolean {
        return user.uid.isNotBlank()
    }
}