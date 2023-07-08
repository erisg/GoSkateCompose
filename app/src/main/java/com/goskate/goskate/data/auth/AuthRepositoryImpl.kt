package com.goskate.goskate.data.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.goskate.goskate.domain.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor() : AuthRepository {
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val database: FirebaseDatabase by lazy { FirebaseDatabase.getInstance() }

    override suspend fun signUp(email: String, password: String, name: String, age: String): Flow<Result<User>> =
        callbackFlow {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        user?.let { currentUser ->
                            val userData = User(
                                uid = currentUser.uid,
                                name = name,
                                email = email,
                                age = age,
                            )
                            database.reference.child("users").child(currentUser.uid).setValue(userData)
                                .addOnCompleteListener { userDataTask ->
                                    if (userDataTask.isSuccessful) {
                                        trySend(Result.success(User()))
                                    } else {
                                        trySend(Result.failure(userDataTask.exception ?: Exception("User data save failed")))
                                    }
                                    close()
                                }
                        } ?: run {
                            trySend(Result.failure(Exception("User is null")))
                            close()
                        }
                    } else {
                        trySend(Result.failure(task.exception ?: Exception("Sign-up failed")))
                        close()
                    }
                }
            awaitClose()
        }.flowOn(Dispatchers.IO)

    override suspend fun signIn(email: String, password: String): Flow<Result<User>> =
        callbackFlow {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        trySend(Result.success(User()))
                    } else {
                        trySend(Result.failure(task.exception ?: Exception("Sign-in failed")))
                    }
                    close()
                }
            awaitClose()
        }.flowOn(Dispatchers.IO)
    fun signOut() {
        auth.signOut()
    }
}
