package com.eltescode.auth_data.authenticators

import com.eltescode.auth_domain.authenticator.Authenticator
import com.eltescode.auth_domain.authenticator.Result
import com.eltescode.auth_domain.model.CustomUser
import com.eltescode.auth_domain.utils.EmailAndPasswordCredentials
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class FirebaseEmailAndPasswordAuthenticator(
    private val auth: FirebaseAuth
) : Authenticator<EmailAndPasswordCredentials> {
    override suspend fun signIn(credentials: EmailAndPasswordCredentials): Result {
        return try {
            auth.signInWithEmailAndPassword(credentials.email, credentials.password).await()
            Result.Success
        } catch (e: Exception) {
            return Result.Error(e.message)
        }
    }

    override suspend fun signUp(credentials: EmailAndPasswordCredentials): Result {
        return try {
            auth.createUserWithEmailAndPassword(credentials.email, credentials.password).await()
            Result.Success
        } catch (e: Exception) {
            return Result.Error(e.message)
        }
    }

    override fun signOut(): Result {
        return try {
            auth.signOut()
            Result.Success
        } catch (e: Exception) {
            return Result.Error(e.message)
        }
    }

    override fun getCurrentUser(): CustomUser? {
        return auth.currentUser?.mapToCustomUser()
    }

}

private fun FirebaseUser.mapToCustomUser(): CustomUser = CustomUser(
    photo = photoUrl.toString(),
    uid = uid,
    email = email
)