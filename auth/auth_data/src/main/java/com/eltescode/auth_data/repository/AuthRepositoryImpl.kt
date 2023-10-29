package com.eltescode.auth_data.repository

import com.eltescode.auth_data.authenticators.FirebaseEmailAndPasswordAuthenticator
import com.eltescode.auth_domain.authenticator.Result
import com.eltescode.auth_domain.model.CustomUser
import com.eltescode.auth_domain.repository.AuthRepository
import com.eltescode.auth_domain.utils.EmailAndPasswordCredentials
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private val authenticator: FirebaseEmailAndPasswordAuthenticator,
    private val fireStore: FirebaseFirestore,
    private val storage: FirebaseStorage
) : AuthRepository {
    override suspend fun <T> signIn(credentials: T): Result {
        return try {
            authenticator.signIn(credentials as EmailAndPasswordCredentials)
        } catch (e: Exception) {
            Result.Error(e.message)
        }
    }

    override suspend fun <T> signUp(credentials: T): Result {
        return try {
            authenticator.signIn(credentials as EmailAndPasswordCredentials)
        } catch (e: Exception) {
            Result.Error(e.message)
        }
    }

    override fun signOut(): Result {
        return try {
            authenticator.signOut()
        } catch (e: Exception) {
            Result.Error(e.message)
        }
    }

    override fun getCurrentUser(): CustomUser? {
        return authenticator.getCurrentUser()
    }

    private suspend fun createNewUser(newUser: CustomUser): Result {
        return try {
            if (isUserValid(newUser)) {
                fireStore.collection("users").document(newUser.uid).set(newUser).await()
                Result.Success
            } else {
                throw Exception("User id is empty string")
            }
        } catch (e: Exception) {
            Result.Error(e.message)
        }
    }

    private fun isUserValid(user: CustomUser): Boolean {
        return user.uid.isNotBlank()
    }
}