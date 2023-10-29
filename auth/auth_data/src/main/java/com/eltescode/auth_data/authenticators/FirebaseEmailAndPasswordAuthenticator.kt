package com.eltescode.auth_data.authenticators

import com.eltescode.auth_domain.authenticator.Authenticator
import com.eltescode.auth_domain.model.CustomUser
import com.eltescode.auth_domain.utils.EmailAndPasswordCredentials
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class FirebaseEmailAndPasswordAuthenticator(
    private val auth: FirebaseAuth
) : Authenticator<EmailAndPasswordCredentials> {
    override suspend fun signIn(credentials: EmailAndPasswordCredentials) {
        auth.signInWithEmailAndPassword(credentials.email, credentials.password).await()

    }

    override suspend fun signUp(credentials: EmailAndPasswordCredentials) {
        auth.createUserWithEmailAndPassword(credentials.email, credentials.password).await()
    }

    override fun signOut() {
        auth.signOut()
    }

    override fun getCurrentUser(): CustomUser? {
        return auth.currentUser?.mapToCustomUser()
    }

}

private fun FirebaseUser.mapToCustomUser(): CustomUser = CustomUser(
    uid = uid,
    email = email
)