package com.spit91.maskani.data.local.remote.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.spit91.maskani.domain.model.repository.SavedPropertyRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.cancellation.CancellationException


@Singleton
class FirestoreSavedPropertyRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
): SavedPropertyRepository {
    //users uid or null if nobody is signed in
    private fun savedCollection(): CollectionReference? {
        val uid = firebaseAuth.currentUser?.uid ?: return null
        return firestore.collection("users").document(uid).collection("saved")

    }
    override suspend fun getSavedPropertyIds(): Result<List<String>> {
        return try {
            val collection = savedCollection()
                ?: return Result.failure(Exception("No signed-in user"))
            val snapshot = collection
                .orderBy("savedAt", Query.Direction.DESCENDING)
                .get()
                .await()
            Result.success(snapshot.documents.map{ it.id})
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun saveProperty(propertyId: String): Result<Unit> {
        return try {
            val collection = savedCollection()
                ?: return Result.failure(Exception("No signed-in user"))
            collection.document(propertyId)
                .set(
                    mapOf(
                        "propertyId" to propertyId,
                        "savedAt" to FieldValue.serverTimestamp()
                    )
                )
                .await()
            Result.success(Unit)
        }catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun unsaveProperty(propertyId: String): Result<Unit> {
        return try{
            val collection = savedCollection()
                ?: return Result.failure(Exception ("No signed-in user."))
            collection.document(propertyId).delete().await()
            Result.success(Unit)
        } catch (e:CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}