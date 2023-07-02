package com.goskate.goskate.data.auth

import com.google.firebase.auth.FirebaseAuth
import com.goskate.goskate.domain.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor() : AuthRepository {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override suspend fun signUp(email: String, password: String) = flow {
        try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            emit(Result.success("OK"))
        } catch (e: Exception) {
            emit(Result.failure(Throwable("Algo falló. Inténtalo de nuevo")))
        }
    }.catch {
        emit(Result.failure(Throwable("Algo fallo intenta de nuevo")))
    }

    override suspend fun signIn(email: String, password: String): Flow<Result<User>> = flow {
        try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            emit(Result.success(User()))
        } catch (e: Exception) {
            emit(Result.failure(Throwable("Algo falló. Inténtalo de nuevo")))
        }
    }.catch {
        emit(Result.failure(Throwable("Algo fallo intenta de nuevo")))
    }
    fun signOut() {
        firebaseAuth.signOut()
    }
}
