package com.spit91.maskani.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.spit91.maskani.domain.model.AuthUser
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import com.google.firebase.auth.FirebaseUser
import  com.spit91.maskani.domain.model.repository.AuthRepository
import kotlinx.coroutines.suspendCancellableCoroutine
@Singleton
class FirebaseAuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    // 1. REAL-TIME FLOW: Streams user login state shifts automatically
    override val currentUser: Flow<AuthUser?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            val firebaseUser = auth.currentUser
            if (firebaseUser != null) {
                trySend(
                    AuthUser(
                        id = firebaseUser.uid,
                        email = firebaseUser.email.orEmpty(),
                        displayName = firebaseUser.displayName
                    )
                )
            } else {
                trySend(null)
            }
        }
        firebaseAuth.addAuthStateListener(listener)
        awaitClose { firebaseAuth.removeAuthStateListener(listener) }
    }

    // 2. SIGN IN: Converts Firebase's callback into a modern coroutine suspend function
    override suspend fun signInWithEmail(email: String, password: String): kotlin.Result<AuthUser> {
        return suspendCancellableCoroutine { continuation ->
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener { result ->
                    val user = result.user
                    if (user != null) {
                        continuation.resume(
                            kotlin.Result.success(
                                AuthUser(
                                    id = user.uid,
                                    email = user.email.orEmpty(),
                                    displayName = user.displayName
                                )
                            )
                        )
                    } else {
                        continuation.resume(kotlin.Result.failure(Exception("User payload came back null.")))
                    }
                }
                .addOnFailureListener { exception ->
                    continuation.resume(kotlin.Result.failure(exception))
                }
        }
    }

    // 3. SIGN UP: Creates a new user profile on Firebase servers
    override suspend fun signUpWithEmail(email: String, password: String, name: String): kotlin.Result<AuthUser> {
        return suspendCancellableCoroutine { continuation ->
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { result ->
                    val user = result.user
                    if (user != null) {
                        continuation.resume(
                            kotlin.Result.success(
                                AuthUser(
                                    id = user.uid,
                                    email = user.email.orEmpty(),
                                    displayName = name
                                )
                            )
                        )
                    } else {
                        continuation.resume(kotlin.Result.failure(Exception("User generation failed.")))
                    }
                }
                .addOnFailureListener { exception ->
                    continuation.resume(kotlin.Result.failure(exception))
                }
        }
    }

    // 4. SIGN OUT: Completely tears down the active session tokens
    override suspend fun signOut() {
        firebaseAuth.signOut()
    }
}