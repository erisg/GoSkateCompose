package com.goskate.goskate.data.auth

import com.google.firebase.auth.FirebaseAuth
import com.goskate.goskate.domain.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor() : AuthRepository {
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override suspend fun signUp(email: String, password: String) = flow {
        try {
            auth.createUserWithEmailAndPassword(email, password).await()
            emit(Result.success("OK"))
        } catch (e: Exception) {
            emit(Result.failure(Throwable("Algo falló. Inténtalo de nuevo")))
        }
    }.catch {
        emit(Result.failure(Throwable("Algo fallo intenta de nuevo")))
    }

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
