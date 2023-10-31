package com.eltescode.user_data.repository

import com.eltescode.core_domain.utils.Result
import com.eltescode.user_domain.model.CustomUserData
import com.eltescode.user_domain.repository.CurrentUserId
import com.eltescode.user_domain.repository.UserDataRepository
import com.eltescode.user_domain.repository.UserPhotoUrl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class UserDataRepositoryImpl(
    private val auth: FirebaseAuth,
    private val fireStore: FirebaseFirestore,
    private val storage: FirebaseStorage
) : UserDataRepository {
    override fun signOut(): Result {
        return try {
            auth.signOut()
            Result.Success
        } catch (e: Exception) {
            Result.Error(e.message)
        }
    }

    override suspend fun getUserData(): kotlin.Result<CustomUserData> {
        return try {
            val user = getCurrentUserId()?.let {
                fireStore.collection("users")
                    .document(it)
                    .get()
                    .await().toObject(CustomUserData::class.java)
            } ?: throw Exception("No such user")
            kotlin.Result.success(user)
        } catch (e: Exception) {
            kotlin.Result.failure(e)
        }
    }


    override suspend fun editUserPersonalData(newUserData: CustomUserData): Result {
        return try {
            fireStore
                .collection("users")
                .document(newUserData.uid)
                .set(newUserData).await()
            Result.Success
        } catch (e: Exception) {
            Result.Error(e.message)
        }
    }


    override suspend fun uploadUserPhoto(bytes: ByteArray): kotlin.Result<UserPhotoUrl> {
        val userId = auth.currentUser?.uid
        return try {
            val photoUri = userId?.let {
                storage.getReference("users")
                    .child(it)
                    .putBytes(bytes)
                    .await().storage.downloadUrl.await()
            }
            kotlin.Result.success(photoUri.toString())
        } catch (e: Exception) {
            e.printStackTrace()
            kotlin.Result.failure(e)
        }
    }

    private fun getCurrentUserId(): CurrentUserId {
        return auth.currentUser?.uid
    }

}
